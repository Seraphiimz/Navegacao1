package com.example.navegacao1.ui.telas

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navegacao1.model.dados.Endereco
import com.example.navegacao1.model.dados.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier) {
    var scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(10.dp)) {
        Text(text = "Tela Principal")

        var endereco by remember { mutableStateOf(Endereco()) }
        var cepInput by remember { mutableStateOf("") }
        var enderecoCarregado by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            scope.launch {
                Log.d("principal", "aqui")
            }
        }

        OutlinedTextField(
            value = cepInput,
            onValueChange = { cepInput = it },
            label = { Text(text = "Digite o CEP") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                scope.launch {
                    Log.d("principal", "Buscando endereço para o CEP: $cepInput")
                    endereco = getEndereco(cepInput)
                    enderecoCarregado = true
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.DarkGray),
        ) {
            Text("Buscar Endereço")
        }

        if (enderecoCarregado) {
            Text(text = "Logradouro: ${endereco.logradouro}")
            Text(text = "Bairro: ${endereco.bairro}")
            Text(text = "Cidade: ${endereco.localidade}")
            Text(text = "UF: ${endereco.uf}")
        }
    }
}

suspend fun getEndereco(cep: String): Endereco {
    return withContext(Dispatchers.IO) {
        RetrofitClient.usuarioService.getEndereco(cep)
    }
}
