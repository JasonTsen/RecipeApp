package com.example.recipeapp


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.recipe_add.*
import java.io.File
import java.io.IOException


@Suppress("DEPRECATION")
class AddRecipe : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val PICK_IMAGE_REQUEST = 2
    private lateinit var foodViewModel : FoodViewModel
    private var filePath: Uri? = null

    var food: Food? = null
    private var mCurrentPhoto: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_add)


        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        // bind the recipetypes.xml into the spinners
        val spinnerName: Spinner = findViewById(R.id.spinnerName)
        spinnerName.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this, R.array.food_name_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerName.adapter = adapter
        }
        val spinnerIngr: Spinner = findViewById(R.id.spinnerIngr)
        spinnerIngr.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this, R.array.food_ingr_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerIngr.adapter = adapter
        }
        val spinnerSteps: Spinner = findViewById(R.id.spinnerSteps)
        spinnerSteps.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this, R.array.food_steps_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSteps.adapter = adapter
        }

        val spinnerCat: Spinner = findViewById(R.id.spinnerCat)
        spinnerCat.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this, R.array.food_cat_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCat.adapter = adapter
        }
        // initiate choose image function
        btnImage.setOnClickListener {
            ImagePicker()
        }
        val btn = findViewById<Button>(R.id.btnEdit)
        btn.setOnClickListener {
            if (filePath != null) {

                val spinName = spinnerName.getSelectedItem().toString()
                val spinIngr = spinnerIngr.getSelectedItem().toString()
                val spinSteps = spinnerSteps.getSelectedItem().toString()
                val spinCat = spinnerCat.getSelectedItem().toString()
                //convert filePath as Uri to String as database image stored as string
                val img = filePath.toString()
                val intent = Intent()

                // pass data into intent
                intent.putExtra("foodName", spinName)
                intent.putExtra("foodImg", img)
                intent.putExtra("foodIngr", spinIngr)
                intent.putExtra("foodSteps", spinSteps)
                intent.putExtra("foodCtgry", spinCat)

                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Adding....")

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else {
                Toast.makeText(this, "Please Choose an Image", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
    // the type is set to image/* to auto go in the gallery
    private fun ImagePicker(){
        val intent = Intent()

        intent.setType("image/*")
        intent.action = Intent.ACTION_OPEN_DOCUMENT

        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            // when user selected image, it will pass to filePath
            filePath = data.data!!
                try{

                    val source = filePath?.let {
                        ImageDecoder.createSource(
                            this.contentResolver,
                            it
                        )
                    }

                    val bitmap = source?.let{ImageDecoder.decodeBitmap(it)}
                    addImageView?.setImageBitmap(bitmap)
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }



        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            parent.getItemAtPosition(pos)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }


}


