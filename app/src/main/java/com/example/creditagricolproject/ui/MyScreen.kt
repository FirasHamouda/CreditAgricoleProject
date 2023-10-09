import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.creditagricolproject.domaine.model.Account
import com.example.creditagricolproject.domaine.model.ResponseData
import com.example.creditagricolproject.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    uiState: UiState,
    response: ResponseData,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mes comptes",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 42.sp
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (!uiState.error.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = uiState.error)
                }
            } else {
                val caAccounts = mutableListOf<Account>()
                val otherAccounts = mutableListOf<Account>()

                response.data.forEach { accountsResponse ->
                    val sortedAccounts = accountsResponse.accounts.sortedBy { it.label }
                    if (accountsResponse.isCA == 1) {
                        caAccounts.addAll(sortedAccounts)
                    } else {
                        otherAccounts.addAll(sortedAccounts)
                    }
                }

                var expanded by remember { mutableStateOf(false) }
                var collapced by remember { mutableStateOf(false) }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    if (caAccounts.isNotEmpty()) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { expanded = !expanded }
                            ) {
                                Icon(
                                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                                Text(
                                    text = "Comptes Crédit Agricole",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                        }
                        if (expanded) {
                            items(caAccounts) { account ->
                                val route = "detailScreen/${account.id}"
                                val onClick: () -> Unit = {
                                    navController.navigate(route)
                                }

                                BankAccountSection(
                                    title = account.label,
                                    account = account,
                                    onClick = onClick
                                )
                            }
                        }

                    }


                    if (otherAccounts.isNotEmpty()) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { collapced = !collapced }
                            ) {
                                Icon(
                                    imageVector = if (collapced) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                                Text(
                                    text = "Autres Banques",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                        if (collapced) {
                            items(otherAccounts) { account ->
                                val route = "detailScreen/${account.id}"
                                val onClick: () -> Unit = {
                                    navController.navigate(route)
                                }
                                BankAccountSection(
                                    title = account.label,
                                    account = account,
                                    onClick = onClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BankAccountSection(
    title: String,
    account: Account,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${account.balance}€",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}