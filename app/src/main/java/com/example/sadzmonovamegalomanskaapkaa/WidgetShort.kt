package com.example.sadzmonovamegalomanskaapkaa

import DataStoreManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import com.example.sadzmonovamegalomanskaapkaa.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WidgetShort(
    selected: Boolean,
    icon: Painter,
    name: String,
    backgroundColor: Color =
        if (selected)
        {
            MaterialTheme.colorScheme.primary
        }
        else
        {
            MaterialTheme.colorScheme.secondary
        },
    modifier: Modifier = Modifier,

){
    //context
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the dataStore class
    val dataStore = DataStoreManager( context )

    var offsetNow  by remember { mutableStateOf(0f) }
    var offsetPrev by remember { mutableStateOf(0f) }
    var position   by remember { mutableStateOf(0)}

    val listState = rememberLazyListState()

    val visibleItemsInfo = listState.layoutInfo
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, bottom = 16.dp, end = 16.dp)
            .background(
                backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
    )
    {
        //Pictogram of module
        Image(
            painter            = icon,
            contentDescription = "Pictogram of $name",
            modifier = modifier.then(
                if ( name == "Fridge" ) {
                    Modifier.padding( start = 24.dp, top = 16.dp, bottom = 16.dp, end = 16.dp )
                } else {
                    Modifier.padding( 16.dp )
                }
            )
        )
        //Module name
        Text(
            text     = name,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.scrim,
            modifier = modifier.padding( end = 16.dp )
        )
        Box(
            Modifier
                .size(40.dp)
                .scrollable(
                    orientation = Orientation.Vertical,
                    // Scrollable state: describes how to consume
                    // scrolling delta and update offset
                    state = rememberScrollableState { delta ->
                        offsetNow += delta
                        delta
                    }
                )
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            scope.launch{
            if ( offsetNow - offsetPrev > 10 && position <= 3)
            {
                position = position + 1
                offsetPrev = offsetNow
                delay( 100 )
            }
        else if ( offsetNow - offsetPrev < -10 && position >= 1 )
            {
                position = position - 1
                offsetPrev = offsetNow
                delay( 100 )
            }
            }
            Text(text = "$position")
        }



/*        LazyColumn (
            state = listState,
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(width = 30.dp, height = 30.dp)
                .background(
                    MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(8.dp)
                )

        ){
            items(5) { index ->
                Text(
                    "$index",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.scrim,
                    )
                derivedStateOf {
                    dataStore.saveAlarmCount( index )
                }
                //Launch the class in a coroutine scope
                scope.launch {
                    delay( 5000 )
                    dataStore.saveAlarmCount( index )
                }
            }
        }*/
        //Assigning correct getFunctions to names
        val count =
            if( name == "Alarm" )
            {
                dataStore.getAlarmCount()
            }
            else if ( name == "Kettle" )
            {

            }
            else
            {

            }
    }
    Row (Modifier.padding(top= 100.dp)){
        Text(text = "OffsetNow = $offsetNow")
        Text(text = "OffsetPrev = $offsetPrev")
    }
}

@Composable
@Preview
fun Preview()
{
    var swl = true
    AppTheme {
        WidgetShort(
            selected = swl,
            icon = painterResource(id = R.drawable.doorbell),
            name = "Doorbell"
        )
    }
}

