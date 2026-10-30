
package com.example.calculadorakmp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// PALETA DE COLORES
// ==========================================

private val Fondo = Color(0xFFEDF3FA)
private val Panel = Color(0xFFFFFFFF)
private val AzulPrincipal = Color(0xFF168AAD)
private val AzulCientifico = Color(0xFFD7E9F8)
private val AzulNumero = Color(0xFFFFFFFF)
private val Texto = Color(0xFF17365D)
private val TextoSecundario = Color(0xFF66809B)

// ==========================================
// APLICACION PRINCIPAL
// ==========================================

@Composable
fun App() {
    MaterialTheme {
        SmartCalc()
    }
}

// ==========================================
// CALCULADORA CIENTIFICA
// ==========================================

@Composable
fun SmartCalc() {

    val calculadora = remember { Calculadora() }

    var pantalla by remember {
        mutableStateOf(calculadora.pantalla)
    }

    var operacion by remember {
        mutableStateOf(calculadora.operacion)
    }

    // GESTION DE LAS TECLAS

    val pulsarTecla: (String) -> Unit = { tecla ->

        calculadora.pulsar(tecla)

        pantalla = calculadora.pantalla
        operacion = calculadora.operacion
    }

    // DISTRIBUCION DEL TECLADO

    val filas = listOf(
        listOf("C", "⌫", "mod", "÷", "+/−"),
        listOf("sin", "cos", "tan", "√x", "x²"),
        listOf("log", "ln", "exp", "10ˣ", "xʸ"),
        listOf("7", "8", "9", "n!", "×"),
        listOf("4", "5", "6", "1/x", "−"),
        listOf("1", "2", "3", "|x|", "+")
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Fondo
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
                .statusBarsPadding()
                .navigationBarsPadding(),

            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            // ==================================
            // CABECERA
            // ==================================

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "CALCULADORA-KMP",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Texto,
                    letterSpacing = 1.sp
                )

                Spacer(Modifier.height(3.dp))

                Text(
                    text = "CALCULADORA CIENTÍFICA",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 3.sp,
                    color = TextoSecundario
                )
            }

            // ==================================
            // PANTALLA DE RESULTADOS
            // ==================================

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.7f),

                color = Panel,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 3.dp
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 12.dp
                        )
                ) {

                    // OPERACION ANTERIOR
                    // SOLO APARECE CUANDO EXISTE

                    if (operacion.isNotBlank()) {

                        Text(
                            text = operacion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(
                                    rememberScrollState()
                                ),

                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextoSecundario,
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            softWrap = false
                        )
                    }

                    // ESPACIO FLEXIBLE

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    // ETIQUETA RESULTADO

                    Text(
                        text = "RESULTADO",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = TextoSecundario,
                        textAlign = TextAlign.End
                    )

                    Spacer(
                        Modifier.height(3.dp)
                    )

                    // RESULTADO COMPLETO
                    // ALTURA RESERVADA PARA EVITAR RECORTES

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp),

                        contentAlignment = Alignment.CenterEnd
                    ) {

                        Text(
                            text = pantalla,
                            modifier = Modifier.fillMaxWidth(),

                            fontSize = when {
                                pantalla.length > 30 -> 17.sp
                                pantalla.length > 24 -> 20.sp
                                pantalla.length > 18 -> 25.sp
                                pantalla.length > 12 -> 31.sp
                                pantalla.length > 8 -> 37.sp
                                else -> 44.sp
                            },

                            lineHeight = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Texto,
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // ==================================
            // TECLADO CIENTIFICO
            // ==================================

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6.8f),

                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                filas.forEach { fila ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),

                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        fila.forEach { tecla ->

                            BotonCientifico(
                                texto = tecla,

                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),

                                onClick = {
                                    pulsarTecla(tecla)
                                }
                            )
                        }
                    }
                }

                // ==================================
                // ULTIMA FILA
                // ==================================

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),

                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    // CERO

                    BotonCientifico(
                        texto = "0",

                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),

                        onClick = {
                            pulsarTecla("0")
                        }
                    )

                    // DECIMAL

                    BotonCientifico(
                        texto = ".",

                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),

                        onClick = {
                            pulsarTecla(".")
                        }
                    )

                    // IGUAL

                    BotonCientifico(
                        texto = "=",

                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),

                        onClick = {
                            pulsarTecla("=")
                        }
                    )
                }
            }
        }
    }
}

// ==========================================
// COMPONENTE DE LOS BOTONES
// ==========================================

@Composable
fun BotonCientifico(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    // IDENTIFICAR TIPO DE BOTON

    val esOperacion = texto in listOf(
        "C", "⌫", "mod", "÷", "+/−",
        "×", "−", "+", "="
    )

    val esCientifico = texto in listOf(
        "sin", "cos", "tan", "√x", "x²",
        "log", "ln", "exp", "10ˣ", "xʸ",
        "n!", "1/x", "|x|"
    )

    // COLORES

    val colorBoton = when {
        esOperacion -> AzulPrincipal
        esCientifico -> AzulCientifico
        else -> AzulNumero
    }

    val colorTexto = if (esOperacion) {
        Color.White
    } else {
        Texto
    }

    // DIBUJAR BOTON

    Surface(
        modifier = modifier.clickable(
            onClick = onClick
        ),

        shape = RoundedCornerShape(13.dp),
        color = colorBoton,
        shadowElevation = 2.dp
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = texto,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = colorTexto,
                maxLines = 1
            )
        }
    }
}
