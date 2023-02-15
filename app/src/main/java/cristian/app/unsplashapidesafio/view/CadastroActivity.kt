package cristian.app.unsplashapidesafio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import cristian.app.unsplashapidesafio.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCadastroBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configtToolbar()

        binding.btnCadastrar.setOnClickListener {
            cadastrarUsuario()
        }
    }

    private fun cadastrarUsuario() {

        val email = binding.textEmailCadastro.text.toString()
        val senha = binding.textSenhaCadastro.text.toString()

        if (verificarCampos()) {
            auth.createUserWithEmailAndPassword(
                email, senha
            ).addOnSuccessListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Erro ao efetuar cadastro, verifique email e senha, ou cadastre-se novamente",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "Preencha os campos email e senha",
                Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun verificarCampos(): Boolean {

        val email = binding.textEmailCadastro.text.toString()
        val senha = binding.textSenhaCadastro.text.toString()

        if (email.isEmpty() || senha.isEmpty()) {
            return false
        }
        return true
    }

    private fun configtToolbar() {
        supportActionBar?.title = "Cadastrar"
    }
}