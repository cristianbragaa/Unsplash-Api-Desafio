package cristian.app.unsplashapidesafio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cristian.app.unsplashapidesafio.databinding.ItemFotoBinding
import cristian.app.unsplashapidesafio.model.Resultado

class FotosAdapter : RecyclerView.Adapter<FotosAdapter.FotosViewHolder>() {

    private var results = mutableListOf<Resultado>()

    fun getResultApi(list: List<Resultado>) {
        this.results = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotosViewHolder {
        val binding = ItemFotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FotosViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount() = results.count()


    inner class FotosViewHolder(private val binding: ItemFotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val image = binding.imageUrl
        private val descricao = binding.textDescricao
        private val usuarioPostagem = binding.textUsuarioPostagem

        fun bind(result: Resultado) {
            Picasso.get().load(result.urls.small).into(image)
            usuarioPostagem.text = "postado por: ${result.user.name}"

            if (result.description == "" || result.description == null) {
                descricao.text = "--"
            } else {
                descricao.text = result.description
            }
        }
    }
}