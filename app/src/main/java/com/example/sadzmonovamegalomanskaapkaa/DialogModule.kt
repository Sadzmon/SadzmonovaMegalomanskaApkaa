package com.example.sadzmonovamegalomanskaapkaa
//Svihnout tam int aby se to zavrelo o kolo pozdejs

import DataStoreManager
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sadzmonovamegalomanskaapkaa.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DialogModuleChoose()
{
    //context
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the dataStore class
    val dataStore = DataStoreManager( context )

    var selectedAlarm    by rememberSaveable { mutableStateOf( false ) }
    var selectedKettle   by rememberSaveable { mutableStateOf( false ) }
    var selectedFridge   by rememberSaveable { mutableStateOf( false ) }
    var selectedDoorbell by rememberSaveable { mutableStateOf( false ) }

    var showDialog         by rememberSaveable { mutableStateOf( false ) }
    var activateMainScreen by rememberSaveable { mutableStateOf( false ) }
    var confirm            by rememberSaveable { mutableStateOf( false ) }

    var confirm_count by rememberSaveable { mutableStateOf( 0 ) }
    var checkSum by rememberSaveable { mutableStateOf( true ) }

    //Set initial state to false.
    if ( checkSum == true )
    {
        scope.launch {
            dataStore.saveSaveState( false )
        }
        checkSum = false
    }


    if ( confirm == true )
    {
        confirm_count = confirm_count + 1
        Log.i("Confirms: ", "$confirm_count")
        if ( confirm_count == 2 )
        {
            scope.launch {
                dataStore.saveSaveState( true )
            }
            confirm = false
            confirm_count = 0
            showDialog = !showDialog
        }
    }
    
    var info            by rememberSaveable { mutableStateOf( 0 ) }
    var savee           by rememberSaveable { mutableStateOf( false ) }
    info  = dataStore.getAlarmCount()
    savee = dataStore.getSaveState()


    Column {
        Text(text = "AlarmCount = $info")
        Text(text = "AlarmCount = $savee")
    }


    if ( showDialog == true ) {
        Dialog( onDismissRequest = { showDialog = !showDialog } ) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(575.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(470.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Please choose your module:",
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        WidgetShort(
                            selected = selectedAlarm,
                            icon = painterResource(id = R.drawable.alarm),
                            name = "Alarm",
                            modifier = Modifier.clickable { selectedAlarm = !selectedAlarm }
                        )
                        WidgetShort(
                            selected = selectedKettle,
                            icon = painterResource(id = R.drawable.kettle),
                            name = "Kettle",
                            modifier = Modifier.clickable { selectedKettle = !selectedKettle }
                        )
                        WidgetShort(
                            selected = selectedFridge,
                            icon = painterResource(id = R.drawable.fridge),
                            name = "Fridge",
                            modifier = Modifier.clickable { selectedFridge = !selectedFridge }
                        )
                        WidgetShort(
                            selected = selectedDoorbell,
                            icon = painterResource(id = R.drawable.doorbell),
                            name = "Doorbell",
                            modifier = Modifier.clickable { selectedDoorbell = !selectedDoorbell }
                        )
                        Log.e( "Widget recomposition:", "Works?" )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { showDialog = !showDialog },
                            modifier = Modifier.padding(4.dp),
                        ) {
                            Text(
                                fontSize = 28.sp,
                                text = "Dismiss"
                            )
                        }
                        TextButton(
                            onClick = {
                                confirm = !confirm
                                activateMainScreen = true,
                                
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                fontSize = 28.sp,
                                text = "Confirm"
                            )
                        }
                    }
                }

            }
        }
    }

    if ( activateMainScreen == true )
    {
        Column (
            Modifier.fillMaxSize()
        ) {

            Spacer( modifier = Modifier.weight( 1f ))

            Button(
                onClick = { showDialog = !showDialog },
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp)
            ) {
                Text(
                    text = "+",
                    fontSize = 28.sp
                )
            }
        }
    }
    else
    {
        Column (
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                fontSize   = 44.sp,
                modifier   = Modifier.padding( 16.dp ),
                textAlign  = TextAlign.Center,
                lineHeight = 50.sp,
                text = "Click on the + button to add module"
            )
            Button(
                onClick  = { showDialog = !showDialog },
                modifier = Modifier.size( 80.dp ),  //avoid the oval shape
                shape    = CircleShape,
                border   = BorderStroke( 1.dp, MaterialTheme.colorScheme.primary ),
                contentPadding = PaddingValues( 0.dp ),  //avoid the little icon
                colors   = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.primary ),
            ) {
                Text(
                    text = "+",
                    fontSize = 56.sp
                )
            }
        }
    }
}

@Preview
    ( showSystemUi = true )
@Composable
fun DialogModulePreview()
{
    AppTheme {
            DialogModuleChoose()
    }
}