import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.md.satuwargaapp.R
import com.md.satuwargaapp.databinding.FragmentSaranBinding

class SaranFragment : Fragment() {

    private var _binding: FragmentSaranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKirim.setOnClickListener {
            val isi = binding.etSaran.text.toString()
            if (isi.isNotBlank()) {
                showSaranSuccessDialog()
                binding.etSaran.text.clear()
            } else {
                Toast.makeText(requireContext(), "Isi saran terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSaranSuccessDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_saran_berhasil, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.setCancelable(true)

        val btnOkay = dialogView.findViewById<Button>(R.id.btnOkay)
        btnOkay.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

