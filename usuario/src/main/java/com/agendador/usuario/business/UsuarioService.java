package com.agendador.usuario.business;

import com.agendador.usuario.controller.DTO.endereco.EnderecoDTO;
import com.agendador.usuario.controller.DTO.endereco.ShowEnderecoDTO;
import com.agendador.usuario.controller.DTO.telefone.ShowTelefoneDTO;
import com.agendador.usuario.controller.DTO.telefone.TelefoneDTO;
import com.agendador.usuario.controller.DTO.usuario.ShowUsuarioDTO;
import com.agendador.usuario.controller.DTO.usuario.UsuarioDTO;
import com.agendador.usuario.controller.converter.UsuarioConverter;
import com.agendador.usuario.infrastructure.entity.Endereco;
import com.agendador.usuario.infrastructure.entity.Telefone;
import com.agendador.usuario.infrastructure.entity.Usuario;
import com.agendador.usuario.infrastructure.exceptions.ConflictException;
import com.agendador.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.agendador.usuario.infrastructure.repository.EnderecoRepository;
import com.agendador.usuario.infrastructure.repository.TelefoneRepository;
import com.agendador.usuario.infrastructure.repository.UsuarioRepository;
import com.agendador.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final JwtUtil jwtUtil;

    public ShowUsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        try{
            //Verifica se o e-mail existe
            emailExiste(usuarioDTO.getEmail());
            //Recebe os valores do usuário DTO e copia para a entidade Usuário
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            //Salva os dados recebidos pela entidade DTO no banco de dados
            usuario = usuarioRepository.save(usuario);
            //Retorna o ShowUsuarioDTO onde não mostrará a senha do usuário
            return new ShowUsuarioDTO(usuario);

        }catch(ConflictException e){
            throw new ConflictException("Email já existente: " + e.getCause());
        }
    }

    //Funcionalidade que verifica dentro do banco de dados se o email é existente
    public boolean verificaEmailExiste(String email){
        return usuarioRepository.existsByEmail(email);
    }

    //Funcionalidade que chama o metodo verificaEmail e faz os tratamentos necessários
    public void emailExiste(String email){
        try {
            //Verifica se o e-mail já existe
            boolean existe = verificaEmailExiste(email);
            //Condicional que verifica, caso e-mail exista gerará uma Exception de conflito personalizada. Caso de erro entratrá no catch se não seguira normalmente.
            if (existe){
                throw new ConflictException("Email já cadastrado!");
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    //Metodo que deleta o usuário pelo e-mail também podemos utilizar pelo id.
    public void deleteUserByEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    //Metodo que procura os dados do usuário pelo e-mail, também é possivel procurar pelo id.
    //OBS: As opções de busca são personalizadas de acordo com a necessidade e regra de negócio, podendo ser buscado por identificadores unicos como e-mail, cpf, rg, id e etc.
    public ShowUsuarioDTO findUserByEmail(String email){
        return new ShowUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email de usuário não encontrado!")));
    }

    //Metodo que atualiza os dados do usuário.
    public ShowUsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
        //Variavel que receberá o e-mail do metodo extractUserName, onde será passado o token e será extraido o email.
        String email = jwtUtil.extractUsername(token.substring(7));
        //Construtor que recebe os dados do usuário após a busca por e-mail no banco de dados pelo metodo findByEmail no repository
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado!"));
        //Passando os valores recebidos pelo usuarioDTO na requisição e os valores recebidos da entity, onde será feito a comparação dentro do metodo updateUsuario
        //Caso o usuario altere qualquer valor sendo senha, email, idade, ou nome será passado um novo valor para entidade antes de ser salvo no bancod e dados
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);
        return new ShowUsuarioDTO(usuarioRepository.save(usuario));
    }

    public ShowEnderecoDTO atualizandoEnderedo(UUID id, EnderecoDTO enderecoDTO){
        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do endereço não encontrado! " + id));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);
        return new ShowEnderecoDTO(enderecoRepository.save(endereco));
    }

    public ShowEnderecoDTO buscaEnderecoById(UUID id){
        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do endereço não encontrado " + id));
        return new ShowEnderecoDTO(enderecoEntity);
    }

    public void deleteEnderecoById(UUID id){
        enderecoRepository.deleteById(id);
    }

    public ShowTelefoneDTO atualizandoTelefone(UUID id, TelefoneDTO telefoneDTO){
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do telefone não encontrado " + id));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);
        return new ShowTelefoneDTO(telefoneRepository.save(telefone));
    }

    public ShowTelefoneDTO buscaTelefoneById(UUID id){
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID do telefone não encontrado " + id));
        return new ShowTelefoneDTO(telefoneEntity);
    }

    public void deleteTelefoneBydId(UUID id){
        telefoneRepository.deleteById(id);
    }


}
