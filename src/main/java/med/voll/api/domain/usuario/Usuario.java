package med.voll.api.domain.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")      //anotação da JPA
@Entity(name = "Usuario")      //anotação da JPA

@Getter                       //anotação do Lombok  - cria os getters
@NoArgsConstructor            //anotação do Lombok  - cria o construtor sem argumentos
@AllArgsConstructor           //anotação do Lombok  - cria o contrutor com todos os argumentos
@EqualsAndHashCode(of = "id") //anotação do Lombok  - cria o equals e o hashcode
//public class Usuario {
public class Usuario implements UserDetails{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)    //anotação da JPA
	private Long   id;

	private String login;

	private String senha;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));               //alterar
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;                              //alterar para senha
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;                             //alterar para login
	}
}
