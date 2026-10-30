package br.com.senai.duvidas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duvida")
@CrossOrigin(origins = "*") // Libera o acesso para qualquer porta do frontend (Expo, Web, etc.)
public class DuvidaController {

    @Autowired
    private DuvidaRepository repository;

    // Lista todas as dúvidas
    @GetMapping
    public List<Duvida> listar() {
        return repository.findAll();
    }

    // Cadastra uma nova dúvida
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroDuvida dados) {
        repository.save(new Duvida(dados));
    }

    // Atualiza a resposta de uma dúvida pelo professor
    @PutMapping
    @Transactional
    public ResponseEntity<Void> responder(@RequestBody DadosAtualizacaoDuvida dados) {
        var duvida = repository.findById(dados.id());
        
        if (duvida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var duvidaEncontrada = duvida.get();
        duvidaEncontrada.setResposta(dados.resposta());

        return ResponseEntity.ok().build();
    }

    // Exclui uma dúvida pelo ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}