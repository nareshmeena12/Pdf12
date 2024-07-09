package com.example.pdf
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.OpenMultipleDocuments
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pdf.ui.theme.PdfTheme
import org.apache.pdfbox.io.MemoryUsageSetting
import org.apache.pdfbox.multipdf.PDFMergerUtility
import org.apache.pdfbox.multipdf.Splitter
import org.apache.pdfbox.multipdf.PageExtractor
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.encryption.AccessPermission
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory.createFromImage
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PdfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Tools(modifier=Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun MainScreen(modifier: Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {BottomMainBar()}) { innerPadding ->
        Greeting(name = "Android",
            modifier = Modifier.padding(innerPadding))
    }
}
@Composable
fun BottomMainBar() {
    BottomAppBar(modifier = Modifier.height(100.dp),
        actions = {
            Spacer(modifier = Modifier.width(5.dp))
            IconButton(onClick = {
                /* TODO: Handle home icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.35F))
            IconButton(onClick = {
                /* TODO: Handle folder icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.folder_closed),
                    contentDescription = "Folder Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.7F))

            IconButton(onClick = {
                /* TODO: Handle tools icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.tools),
                    contentDescription = "Tools",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    )
}
@Composable
fun Files(modifier: Modifier){
    Column(horizontalAlignment = Alignment.Start){
        Spacer(modifier = Modifier.height(102.dp))
        Row{
            Spacer(modifier = Modifier.width(18.dp))
            Text(text = "My Storage",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Left,
                fontStyle = FontStyle.Normal,
                fontSize = 34.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /*TODO*/ },
            modifier= Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RectangleShape,
            colors =ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = Color.White)
        ) {
            Row(modifier=Modifier.fillMaxSize()
                ,horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.internal_storage),
                    contentDescription ="Internal Storage" )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Internal Storage",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp
                )
            }

        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /*TODO*/ },
            modifier= Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RectangleShape,
            colors =ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = Color.White)
        ) {
            Row(modifier=Modifier.fillMaxSize()
                ,horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.downloads),
                    contentDescription ="Downloads" )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Downloads",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp
                )
            }

        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /*TODO*/ },
            modifier= Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RectangleShape,
            colors =ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = Color.White)
        ) {
            Row(modifier=Modifier.fillMaxSize()
                ,horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.processed_files),
                    contentDescription ="Processed Files" )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Processed Files",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp
                )
            }

        }
    }
}
@Composable
fun Tools(modifier: Modifier){
    val context= LocalContext.current
 //   var directoryCreated by remember { mutableStateOf<Boolean?>(null) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(100.dp))
        Row {
        Button(onClick = { /*TODO*/ },
            modifier=Modifier.size(100.dp),
            shape = RectangleShape) {
                 Text(text = "Image To PDF", style = MaterialTheme.typography.bodyLarge)
        }
            Spacer(modifier = Modifier.width(40.dp))
        Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "PDF To Image", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(40.dp))
            MergePDFComplete()

    }
        Spacer(modifier = Modifier.height(40.dp))
        Row {
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "Split PDF", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "Lock PDF", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "Extract Text", style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "Image To PDF", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "Image To PDF", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.size(100.dp),
                shape = RectangleShape) {
                Text(text = "Image To PDF", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
@Composable
fun MergePDFScreen(){

}
fun splitPdfIn_SinglePages(inputFile:PDDocument):List<PDDocument>{
    val splitter=Splitter()
    val pages=splitter.split(inputFile)
    return pages
}
fun splitPdfIn_Range(inputFile: PDDocument,startPage:Int,endPage:Int){
   val  splitter=Splitter()
    splitter.setStartPage(startPage)
    splitter.setEndPage(endPage)
    splitter.setSplitAtPage(endPage-startPage+1)
    val pdf=splitter.split(inputFile)
    inputFile.close()
}
fun splitPdfIn_Range_SinglePages(inputFile: PDDocument,startPage: Int,endPage: Int):List<PDDocument>{
    val splitter=Splitter()
    splitter.setEndPage(endPage)
    splitter.setStartPage(startPage)
    val docs=splitter.split(inputFile)
    return docs
}
fun mergePdf(outputFile: String,vararg inputFiles: File){
       val merger=PDFMergerUtility()
        for(file in inputFiles){
            merger.addSource(file)
        }
    merger.destinationFileName=outputFile
    merger.mergeDocuments(null)
}
fun lockPDF(inputFile: File,password:String,outputFile: File){
    val document=Loader.loadPDF(inputFile)
    val accessPermission=AccessPermission()
    val protectionPolicy=StandardProtectionPolicy(password,password,accessPermission).apply {
        encryptionKeyLength=128
        permissions=accessPermission
    }
    document.protect(protectionPolicy)
    document.save(outputFile)
}
fun imageToPdf(){

}
fun pdfToImage(){

}
@Composable
fun MergePDFComplete(){
    var selectedFilesUri= remember { mutableListOf<Uri>() }
    val launcher= rememberLauncherForActivityResult(
        contract = OpenMultipleDocuments()) { uris: List<Uri> ->
        uris?.let{
            selectedFilesUri.clear()
            selectedFilesUri.addAll(it)
        }
    }
    Button(onClick = {
        launcher.launch(arrayOf("application/pdf"))
        mergePdf("")

    },
        modifier=Modifier.size(100.dp),
        shape = RectangleShape) {
        Text(text = "Merge PDF", style = MaterialTheme.typography.bodyLarge)
    }
}



fun pdfDire(context: Context) {
    // Create HelloPdf directory
    val externalStorage = context.getExternalFilesDir(null)
    val helloPdfDirectory = File(externalStorage, "HelloPdf")
    if (!helloPdfDirectory.exists()) {
        val created = helloPdfDirectory.mkdirs()
        if (created) {
            Log.d("pdfDire", "HelloPdf directory created successfully, Path: ${helloPdfDirectory.absolutePath}")
        } else {
            Log.d("pdfDire", "Failed to create HelloPdf directory, Path: ${helloPdfDirectory.absolutePath}")
        }
    } else {
        Log.d("pdfDire", "HelloPdf directory already exists, Path: ${helloPdfDirectory.absolutePath}")
    }

    // Create subdirectory within HelloPdf directory
    val subDirectory = File(helloPdfDirectory, "Processed Files")
    if (!subDirectory.exists()) {
        val subDirCreated = subDirectory.mkdirs()
        if (subDirCreated) {
            Log.d("pdfDire", "SubDirectory created successfully within HelloPdf, Path: ${subDirectory.absolutePath}")
        } else {
            Log.d("pdfDire", "Failed to create SubDirectory within HelloPdf, Path: ${subDirectory.absolutePath}")
        }
    } else {
        Log.d("pdfDire", "SubDirectory already exists within HelloPdf, Path: ${subDirectory.absolutePath}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PdfTheme {
        Greeting("Android")
    }
}