package goalp.model;

public class Goal implements IDependency {

	protected String identification;
	
	public Goal(String identification){
		this.identification = identification;
	}
	
	@Override
	public boolean equals(Object goal){
		return (goal instanceof Goal
				&& ((Goal) goal).identification.equals(identification)); 
	}
}
