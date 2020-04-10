package ca.sheridancollege.bhagatvr.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String firstname;
	@NonNull
	private String lastname;
	@NonNull
	private String phonenumber;
	@NonNull
//	@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;
	@NonNull
	private String encryptedPassword;
	@NonNull
	private Byte enabled;
//	@NonNull
//	private boolean isEnabled;
//	
//	public User() {
//        super();
//        this.isEnabled =false;
//    }
//	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER) 
//	@ManyToMany(cascade=CascadeType.ALL) 
	private List<Role> roles = new ArrayList<Role>();
	
	//@OneToOne
	//private Appointment appointment;
//	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Appointment> appointmentList;

}
