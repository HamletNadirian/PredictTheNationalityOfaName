package nadirian.hamlet.android.predictthenationalityofaname


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val nameArrayList:ArrayList<String>,
                    private val probabilityArrayList: ArrayList<String>)
    : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    inner class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        var name:TextView=itemView.findViewById(R.id.name)
        var probability:TextView = itemView.findViewById(R.id.probability)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.name.text = nameArrayList[position]
        viewHolder.probability.text = probabilityArrayList[position]

    }

    override fun getItemCount(): Int {
        return nameArrayList.size
    }
}