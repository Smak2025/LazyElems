package ru.smak.lazyelems

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.smak.lazyelems.db.internal.Card
import ru.smak.lazyelems.db.internal.CardColor
import ru.smak.lazyelems.db.internal.CardInfo
import ru.smak.lazyelems.ui.theme.LazyElemsTheme
import ru.smak.lazyelems.viewmodels.MainViewModel
import ru.smak.lazyelems.viewmodels.Pages
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val values = viewModel.cards.collectAsState(listOf())
            LazyElemsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.title))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            navigationIcon = {
                                if (viewModel.page != Pages.MAIN) {
                                    IconButton(
                                        onClick = {
                                            viewModel.back()
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.outline_arrow_back_24),
                                            contentDescription = stringResource(R.string.back)
                                        )
                                    }
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { viewModel.showColorMenu = !viewModel.showColorMenu }
                                ) {
                                    Icon(painterResource(R.drawable.baseline_menu_24),
                                        stringResource(R.string.color_select)
                                    )
                                }
                                ColorSelector(
                                    viewModel.colors.map{ it.color },
                                    viewModel.selectedColor,
                                    viewModel.showColorMenu,
                                    onDismiss = { viewModel.showColorMenu = false }
                                ) {
                                    viewModel.selectedColor = it
                                    viewModel.showColorMenu = false
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        if (viewModel.page == Pages.LIST) {
                            FloatingActionButton(onClick = {
                                //viewModel.addValue("some text")
                                viewModel.showDialog = true
                            }) {
                                Icon(
                                    painterResource(R.drawable.baseline_add_24),
                                    contentDescription = stringResource(R.string.add)
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.EndOverlay,
                ) { innerPadding ->
                    Crossfade(
                        targetState = viewModel.page,
                        animationSpec = tween(500),
                    ) { page ->
                        when (page) {
                            Pages.MAIN -> MainContent(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            ) {
                                viewModel.toList()
                            }

                            Pages.LIST -> ListContent(
                                values.value,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            )
                        }
                    }
                    if (viewModel.showDialog) {
                        TextDialog(
                            onDismiss = { viewModel.showDialog = false },
                            onSave = { title, text ->
                                viewModel.addValue(title, text)
                                viewModel.showDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onPageChange: ()->Unit = {},
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.main_content))
        Button(onClick = {
            onPageChange()
        }) {
            Text(stringResource(R.string.to_list))
        }
    }
}

@Composable
fun ListContent(
    list: List<CardInfo>,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (list.isEmpty()) {
            Text(stringResource(R.string.list_content))
        } else {
            LazyVerticalGrid (
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(250.dp)
            ) {
                items(list){ item ->
                    CardWithValue(item, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ListContentPreview(){
    LazyElemsTheme {
        ListContent(
            listOf(),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
fun CardWithValue(
    value: CardInfo,
    modifier: Modifier = Modifier,
){
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = value.cardColor?.color ?: Color.Unspecified)
    ) {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)
        val date = formatter.format(value.card.modified)
        Text(
            value.card.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), 1.dp)
        Text(
            value.card.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), 1.dp)
        Text(
            text = date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            textAlign = TextAlign.End,
        )
    }
}

@Preview
@Composable
fun CardWithValuePreview(){
    LazyElemsTheme {
        CardWithValue(CardInfo(Card(title = "Заголовок", text = "Текст карточки"), CardColor(color = Color.Yellow)))
    }
}

@Composable
fun TextDialog(
    modifier: Modifier = Modifier,
    onDismiss: ()->Unit = {},
    onSave: (String, String)->Unit = { _, _ -> },
){
    var userTitle by remember { mutableStateOf("") }
    var userText by remember { mutableStateOf("") }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onSave(userTitle, userText)
            }){
                Text("Ок")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = userTitle,
                    onValueChange = {
                        userTitle = it
                    },
                    placeholder = {
                        Text(stringResource(R.string.title_placeholder))
                    },
                )
                OutlinedTextField(
                    value = userText,
                    onValueChange = {
                        userText = it
                    },
                    placeholder = {
                        Text(stringResource(R.string.text_placeholder))
                    },
                )
            }
        },
    )
}

@Preview
@Composable
fun TextDialogPreview(){
    LazyElemsTheme {
        TextDialog()
    }
}

@Composable
fun ColorSelector(
    colors: List<Color>,
    selectedColor: Color,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSelect: (Color) -> Unit = {},
){
    DropdownMenu(
        expanded,
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        colors.forEachIndexed { index, color ->
            DropdownMenuItem(
                text = {
                    Text((index + 1).toString())
                },
                onClick = {
                    onSelect(color)
                },
                trailingIcon = {
                    Icon(painter = painterResource(
                        if (color == selectedColor) R.drawable.baseline_check_circle_24 else R.drawable.baseline_circle_48
                    ), null, tint = color)
                },
                colors = MenuDefaults.itemColors(
                    textColor = if (color == selectedColor) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    }
}

@Preview
@Composable
fun ColorSelectorExpandedPreview(){
    MaterialTheme {
        ColorSelector(
            listOf(Color.White, Color.Red, Color.Yellow, Color.Green),
            Color.White,
            true,
        )
    }
}