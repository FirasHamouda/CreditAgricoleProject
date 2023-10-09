import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.creditagricolproject.domaine.model.Account
import com.example.creditagricolproject.domaine.model.Operation
import com.example.creditagricolproject.util.Utils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    account: Account,
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
                            fontSize = 24.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "${account.balance}€",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            )

            Text(
                text = account.label,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = account.operations.sortedWith(
                        compareByDescending(Operation::date).thenBy(
                            Operation::title
                        )
                    )
                ) { operation ->
                    OperationItem(operation = operation)
                }
            }
        }
    }
}


@Composable
fun OperationItem(operation: Operation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = operation.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )

            Text(
                text = Utils().convertUnixTimestampToReadableDate(operation.date.toLong()),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        Text(
            text = "${operation.amount}€",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
    }
}
