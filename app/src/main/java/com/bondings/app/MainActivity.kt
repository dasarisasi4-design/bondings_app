package com.bondings.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage

private val Bg = Color(0xFF100B19)
private val Panel = Color(0xFF1D1528)
private val Pink = Color(0xFFFF4F9A)
private val Purple = Color(0xFF9B5CFF)
private val Muted = Color(0xFFB7AFC2)

data class Person(val name:String,val age:Int,val distance:String,val online:Boolean,val image:String,val tag:String)
private val people = listOf(
    Person("Aarav",25,"1 km away",true,"https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=600","New here"),
    Person("Rohan",27,"2 km away",true,"https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=600","Looking for friends"),
    Person("Kiran",24,"3 km away",false,"https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600","Online recently"),
    Person("Dev",29,"4 km away",true,"https://images.unsplash.com/photo-1535713875002-d1d0cf3771d0?w=600","Open to chat".replace(" ","")),
    Person("Sam",26,"5 km away",false,"https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=600","New here"),
    Person("Aditya",28,"6 km away",true,"https://images.unsplash.com/photo-1504593811423-6dd665756598?w=600","Looking for dates")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { BondingsApp() } }
}

@Composable fun BondingsApp() {
    var tab by remember { mutableStateOf("Discover") }
    var selected by remember { mutableStateOf<Person?>(null) }
    var filter by remember { mutableStateOf(false) }
    var liked by remember { mutableStateOf(setOf<String>()) }
    MaterialTheme(colorScheme = darkColorScheme(background=Bg, surface=Panel, primary=Pink)) {
        Surface(Modifier.fillMaxSize(), color=Bg) {
            Column {
                if (tab == "Discover") {
                    Row(Modifier.fillMaxWidth().padding(horizontal=18.dp, vertical=14.dp), verticalAlignment=Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text("BONDINGS", color=Color.White, fontSize=23.sp, fontWeight=FontWeight.Black, letterSpacing=1.5.sp); Text("Find your kind of connection", color=Muted, fontSize=11.sp) }
                        IconButton(onClick={filter=true}) { Icon(Icons.Default.Tune, null, tint=Color.White) }
                        IconButton(onClick={tab="Likes"}) { Icon(Icons.Default.FavoriteBorder, null, tint=Pink) }
                    }
                    Row(Modifier.padding(horizontal=16.dp, vertical=2.dp), verticalAlignment=Alignment.CenterVertically) {
                        Box(Modifier.size(8.dp).background(Color(0xFF42E69A), CircleShape)); Spacer(Modifier.width(7.dp)); Text("NEAR YOU", color=Color.White, fontSize=11.sp, fontWeight=FontWeight.Bold); Spacer(Modifier.weight(1f)); Text("6 profiles", color=Muted, fontSize=11.sp)
                    }
                    LazyVerticalGrid(columns=GridCells.Fixed(3), modifier=Modifier.weight(1f), contentPadding=PaddingValues(10.dp), horizontalArrangement=Arrangement.spacedBy(7.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) {
                        items(people) { p ->
                            Box(Modifier.fillMaxWidth().aspectRatio(.73f).clip(RoundedCornerShape(13.dp)).clickable { selected=p }) {
                                AsyncImage(p.image, p.name, Modifier.fillMaxSize(), contentScale=ContentScale.Crop)
                                Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xDD08060C)), startY=180f)))
                                if(p.online) Box(Modifier.padding(7.dp).size(9.dp).background(Color(0xFF43E69A), CircleShape).border(1.dp, Bg, CircleShape))
                                Column(Modifier.align(Alignment.BottomStart).padding(7.dp)) {
                                    Text("${p.name}, ${p.age}", color=Color.White, fontSize=12.sp, fontWeight=FontWeight.Bold, maxLines=1, overflow=TextOverflow.Ellipsis)
                                    Text(p.distance, color=Color(0xFFE4DDEB), fontSize=9.sp)
                                }
                                IconButton(onClick={liked=if(p.name in liked) liked-p.name else liked+p.name}, modifier=Modifier.align(Alignment.TopEnd).size(30.dp)) { Icon(if(p.name in liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder, null, tint=if(p.name in liked) Pink else Color.White, modifier=Modifier.size(17.dp)) }
                            }
                        }
                    }
                } else { OtherPage(tab, liked.size) { tab="Discover" } }
                NavigationBar(containerColor=Color(0xFF17101F), contentColor=Muted, tonalElevation=0.dp) {
                    listOf("Discover","Likes","Chat","Premium","Profile").forEach { item ->
                        NavigationBarItem(selected=tab==item, onClick={tab=item}, icon={ Icon(when(item){"Discover"->Icons.Default.GridView;"Likes"->Icons.Default.FavoriteBorder;"Chat"->Icons.Default.ChatBubbleOutline;"Premium"->Icons.Default.WorkspacePremium;else->Icons.Default.Person}, null) }, label={Text(item, fontSize=9.sp)}, colors=NavigationBarItemDefaults.colors(selectedIconColor=Pink, selectedTextColor=Pink, indicatorColor=Color(0xFF38213B), unselectedIconColor=Muted, unselectedTextColor=Muted))
                    }
                }
            }
            if(selected!=null) AlertDialog(onDismissRequest={selected=null}, containerColor=Panel, title={Text("${selected!!.name}, ${selected!!.age}", color=Color.White)}, text={Column { AsyncImage(selected!!.image, null, Modifier.fillMaxWidth().height(210.dp).clip(RoundedCornerShape(12.dp)), contentScale=ContentScale.Crop); Spacer(Modifier.height(10.dp)); Text("${selected!!.distance} • ${selected!!.tag}", color=Muted); Text("Profile preview", color=Color.White) }}, confirmButton={TextButton(onClick={selected=null}){Text("Close", color=Pink)}}, dismissButton={TextButton(onClick={liked=liked+selected!!.name;selected=null}){Text("Like", color=Pink)}})
            if(filter) AlertDialog(onDismissRequest={filter=false}, containerColor=Panel, title={Text("Discover filters", color=Color.White)}, text={Column { Text("Distance", color=Muted); Text("Up to 25 km", color=Color.White); Spacer(Modifier.height(10.dp)); Text("Age range", color=Muted); Text("18–35", color=Color.White); Spacer(Modifier.height(10.dp)); Text("Filters are demo controls in this prototype.", color=Muted, fontSize=12.sp) }}, confirmButton={TextButton(onClick={filter=false}){Text("Apply", color=Pink)}})
        }
    }
}

@Composable private fun OtherPage(tab:String, likes:Int, back:()->Unit) {
    Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement=Arrangement.spacedBy(14.dp)) {
        Text(tab, color=Color.White, fontSize=26.sp, fontWeight=FontWeight.Bold)
        when(tab) {
            "Likes" -> { Text("$likes profiles liked", color=Muted); Text("Your liked profiles will appear here.", color=Muted) }
            "Chat" -> { Text("Your conversations", color=Muted); Text("No conversations yet. Discover someone and say hello!", color=Muted) }
            "Premium" -> {
                Text("More ways to connect", color=Muted)
                PlanCard("BONDINGS PLUS", "₹199 / month", listOf("See who likes you","More profile filters","Ad-free experience"))
                PlanCard("BONDINGS PREMIUM", "₹399 / month", listOf("All Plus features","Profile boosts","Priority discovery"))
                Text("Demo pricing only — payments are not connected.", color=Muted, fontSize=11.sp)
            }
            else -> { Text("Your profile", color=Muted); Text("Set up your photos, bio and preferences.", color=Muted); Button(onClick=back, colors=ButtonDefaults.buttonColors(containerColor=Pink)){Text("Back to Discover")} }
        }
    }
}
@Composable private fun PlanCard(title:String, price:String, features:List<String>) {
    Card(colors=CardDefaults.cardColors(containerColor=Panel), shape=RoundedCornerShape(18.dp)) { Column(Modifier.padding(17.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) { Text(title, color=Pink, fontWeight=FontWeight.Bold); Text(price, color=Color.White, fontSize=22.sp, fontWeight=FontWeight.Bold); features.forEach { Text("✓  $it", color=Muted, fontSize=13.sp) }; OutlinedButton(onClick={}, enabled=false){Text("Coming soon")} } }
}
