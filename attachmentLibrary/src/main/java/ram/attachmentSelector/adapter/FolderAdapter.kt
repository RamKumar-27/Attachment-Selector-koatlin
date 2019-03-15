package ram.attachmentSelector.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_folder.view.*
import ram.attachmentSelector.R
import ram.attachmentSelector.data.model.FolderItem
import java.util.*


class FolderAdapter(private val context: Context, private val clickManager: ClickManager?) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    private val folderNameList = ArrayList<String>()
    private val folderIconList = ArrayList<String>()
    private val folderItemList: MutableList<FolderItem>?
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
        folderItemList = ArrayList()
    }

    fun setFolderItemList(itemList: List<FolderItem>) {
        if (folderItemList == null) return
        folderItemList.clear()
        folderItemList.addAll(itemList)
        notifyDataSetChanged()
    }

    interface ClickManager {
        fun onItemClick(folderItem: FolderItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = inflater.inflate(R.layout.item_folder, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {

        val folderItem = folderItemList!![position]
        holder.setFolderDataToView(folderItem)
    }

    override fun getItemCount(): Int {
        return folderItemList!!.size
    }

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var folderIcon=itemView.iv_folder_image
        private var folderName=itemView.tv_folder_name

        init {
            itemView.setOnClickListener(this)
        }

        fun setFolderDataToView(item: FolderItem) {
            folderName!!.text = item.folderName

            Glide.with(context)
                    .load(item.folderIcon)
                    .apply(RequestOptions().centerCrop().error(R.drawable.ic_folder)
                            .placeholder(R.drawable.ic_folder))
                    .into(folderIcon);
        }

        override fun onClick(view: View) {

            val position = adapterPosition

            if (position < 0) return

            val folderItem = folderItemList!![position]

            clickManager?.onItemClick(folderItem)
        }
    }
}
