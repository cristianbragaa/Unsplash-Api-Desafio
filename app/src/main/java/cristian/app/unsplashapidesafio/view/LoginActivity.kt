package cristian.app.unsplashapidesafio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import cristian.app.unsplashapidesafio.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.textCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.btnLogar.setOnClickListener {
            logarUsuario()
        }
    }

    private fun logarUsuario() {

        val email = binding.textEmailLogin.text.toString()
        val senha = binding.textSenhaLogin.text.toString()

        if (verificarCampos()) {
            auth.signInWithEmailAndPassword(
                email, senha
            ).addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Erro ao efetuar login, verifique email e senha, ou cadastre-se novamente",
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

        val email = binding.textEmailLogin.text.toString()
        val senha = binding.textSenhaLogin.text.toString()

        if (email.isEmpty() || senha.isEmpty()) {
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        val usuarioLogado = auth.currentUser
        if (usuarioLogado != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}