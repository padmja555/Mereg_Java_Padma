package ParentTestCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class randomdate {
	Random random=new Random();
	int year= randon.nextInt(2018-1990 +1) + 1990;
	int day= random.nextInt(28)+1;
	int month=random.nextInt(12);
	LocalDate dob = LocalDate.of(year, day, month);
	String formattedDOB=dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	wait.untill(ExpectecConditions.visibilityOfElementLocated(dob)).sendkeys(formattedDOB)
	System.out.println("Random of date: +formattedDOB ");
}
public class randomrange1{
	Random range = new Random();
	int year = random.nextInt(2018-1990 +1)+1990;
	int month= randon.nextInt(12);
	int day = random.nextInt(28)+1;
	LocaytDate dob= LocatDate.of(Datofformatter.ofpattern("MM?DD/YYYY";))
			wait.untill(EpectedConditions.visibilityodElementLocated(dob))
			system.out.println("Random of date:  formattedDOB");

	}
}
