package com.example.android.politicalpreparedness

import android.content.Intent
import android.net.Uri
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.representative.RepresentativeViewModel

@BindingAdapter("clickUrl")
fun setNewIntent(view:TextView,url:String?){
    url?.let {url ->
        view.setOnClickListener {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            it.context.startActivity(intent)
        }

        }


}
@BindingAdapter("onItemSelected")
fun Spinner.setOnItemSelectedListener(viewModel: RepresentativeViewModel) {
    val item = this.selectedItem.toString()
    viewModel.state.value = item
}