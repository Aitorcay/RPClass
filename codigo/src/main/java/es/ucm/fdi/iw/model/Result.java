package es.ucm.fdi.iw.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Results are associated to a contest and decide if a contest has been passed depending on the score
 *
 * @author aitorcay
 */

@Entity
@NamedQueries({
	@NamedQuery(name="Result.getResult",
	query="SELECT r FROM Result r JOIN r.user u JOIN r.contest c "
			+ "WHERE u.id = :userId "
			+ "AND c.id = :contestId"),
	@NamedQuery(name="Result.hasAnswer",
	query="SELECT COUNT(r) FROM Result r JOIN r.user u JOIN r.contest c "
			+ "WHERE u.id = :userId "
			+ "AND c.id = :contestId"),
	@NamedQuery(name="Result.byContest",
	query="SELECT r FROM Result r JOIN r.contest c JOIN r.user u "
			+ "WHERE c.id = :contestId "
			+ "AND u.roles = 'USER' "
			+ "ORDER BY u.lastName ASC"),
	@NamedQuery(name="Result.numAnswers",
	query="SELECT COUNT(r) FROM Result r JOIN r.contest c JOIN r.answers a JOIN r.user u "
			+ "WHERE a.id = :answerId "
			+ "AND c.id = :contestId "
			+ "AND u.roles = 'USER'"),
	@NamedQuery(name="Result.userRanking",
	query="SELECT r FROM Result r JOIN r.user u JOIN r.contest c "
			+ "WHERE u.roles = 'USER' "
			+ "AND u.enabled = 1 "
			+ "AND c.id = :contestId "
			+ "ORDER BY r.score DESC"),
})

public class Result {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Contest contest;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Answer> answers;
	
	private int correct;
	private double score;
	private boolean passed;
	private boolean perfect;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submitDate;	

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public boolean isPerfect() {
		return perfect;
	}

	public void setPerfect(boolean perfect) {
		this.perfect = perfect;
	}	

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder();

		stb.append("--- RESULTADO ---\n");
		stb.append("Usuario: " + this.user.getUsername() + "\n");
		stb.append("Prueba: " + this.contest.getName() + "\n");	
		if (this.passed) {
			stb.append("Prueba superada \n");
		} else {
			stb.append("Prueba fallida \n");			
		}	
		stb.append("Correctas: " + this.correct + "\n");
		stb.append("Puntuación: " + this.score + "\n");
		stb.append("Entregado: " + this.submitDate + "\n");
		
		for (int i = 0; i < this.answers.size(); i++) {
			stb.append(Integer.toString(i+1) + ": " +this.answers.get(i).toString());
		}
		
	    return stb.toString();
	}
}
