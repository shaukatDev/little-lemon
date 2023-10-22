package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavHostController, appDatabase: AppDatabase) {

    val menuItemsDB by appDatabase.menuItemDao().getAll().observeAsState(emptyList())
    var selectedCategory = remember { mutableStateOf("") }
    val searchPhrase = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection(navController)
        HeroSection(searchPhrase = searchPhrase)
        MenuBreakDownSection(menuItemsDB = menuItemsDB, selectedCategory = selectedCategory)
        MenuItems(
            menuItemsList = menuItemsDB,
            searchPhrase = searchPhrase,
            selectedCategory = selectedCategory
        )
    }
}

@Composable
fun TopSection(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Spacer(modifier = Modifier.width(50.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_header_logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .height(50.dp)
                .width(200.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Image(
            painter = painterResource(id = R.drawable.ic_profile_img),
            contentDescription = "Profile picture",
            modifier = Modifier
                .padding(end = 15.dp)
                .size(50.dp)
                .clip(CircleShape)
                .clickable { navController.navigate(Profile.route) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroSection(searchPhrase: MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4C5E57))
            .padding(16.dp),
    ) {
        Text(
            text = "Little Lemon",
            color = Color(0xFFEECF00),
            fontSize = 45.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Column(modifier = Modifier.weight(0.6f)) {
                Text(
                    text = "Chicago", color = Color.White, fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_hero_img),
                contentDescription = "Sandwich",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )


        }

        TextField(
            label = { Text(text = "Enter search phrase") },
            value = searchPhrase.value,
            onValueChange = {
                searchPhrase.value = it
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEAEAEA)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Search, contentDescription = null, tint = Color.Black
                )
            },
        )


    }
}

@Composable
fun MenuBreakDownSection(selectedCategory: MutableState<String>, menuItemsDB: List<MenuItemDB>) {
    val catItems = menuItemsDB.flatMap { listOf(it.category, it.category) }.distinct()
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            modifier = Modifier.padding(top = 30.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            catItems.forEach {
                Button(
                    onClick = { selectedCategory.value = it },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDEFEE)
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = it, fontWeight = FontWeight.Bold, color = Color(0xFF4C5E57))
                }
            }
        }
    }
}

@Composable
fun MenuItems(
    menuItemsList: List<MenuItemDB>,
    searchPhrase: MutableState<String>,
    selectedCategory: MutableState<String>
) {
    var menuItems =
        if (selectedCategory.value.isNotEmpty()) menuItemsList.filter { it.category == selectedCategory.value } else menuItemsList
    if (searchPhrase.value.isNotEmpty()) {
        menuItems =
            menuItemsList.filter { it.title.contains(searchPhrase.value, ignoreCase = true) }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        items(items = menuItems, itemContent = { menuItem -> MenuItem(menuItem) })
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemDB) {
    Divider(
        thickness = 2.dp, color = Color(0xFFDDDDDD), modifier = Modifier.padding(vertical = 15.dp)
    )
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = menuItem.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = menuItem.description, fontSize = 13.sp, color = Color.Gray)
                Text(
                    text = "$${menuItem.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            GlideImage(
                model = menuItem.image,
                contentDescription = "menu item image",
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    val context = LocalContext.current
    val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "little-lemon").build()
    }
    Home(navController = NavHostController(context), database)
}
