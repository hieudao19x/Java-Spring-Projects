package demo;
import java.util.ArrayList;

import entity.Member;
import service.MemberService;
import service.MemberServiceImpl;

public class Main {

	public static void main(String[] args) {
//		MemberServiceImpl service = new MemberServiceImpl();
		MemberService service = MemberServiceImpl.getInstance();
		
		System.out.println(service.greet(2));
		
		System.out.println(service.getAll());
		ArrayList<Member> list = service.getAll();
		for(Member mem : list) {
			System.out.println(mem.getId() + "," + mem.getName() + "," + mem.getEmail());
		}
		
		System.out.println(service.sumOf(3, 5));
	}

}
