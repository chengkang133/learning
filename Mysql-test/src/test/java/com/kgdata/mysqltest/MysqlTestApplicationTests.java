package com.kgdata.mysqltest;

import com.kgdata.mysqltest.dao.UserDao;
import com.kgdata.mysqltest.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest(classes = MysqlTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class MysqlTestApplicationTests {

	@Autowired
	UserDao userDao;

	@Test
	public void testAddUser() {
		User user = new User();
		user.setName("张三");
		user.setAge(18);
		user.setAddress("陕西西安");
		List<String> hobbies = new ArrayList<>();
		hobbies.add("乒乓球");
		hobbies.add("羽毛球");
		hobbies.add("橄榄球");
		user.setHobbies(hobbies);
		Set<String> cars = new HashSet<>();
		cars.add("丰田");
		cars.add("大众");
		cars.add("本田");
		user.setCars(cars);

		Integer row = userDao.addUser(user);
		assert (row == 1);
	}

	@Test
	public void testGetUser() {
		List<User> users = userDao.getAllUser();
		for (User user : users) {
			System.out.println(user);
		}
	}

	@Test
	public void testUUID() throws ParseException {
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

//		Calendar todayEnd = Calendar.getInstance();
//		System.out.println(todayEnd.getTime());
//		todayEnd.add(Calendar.SECOND, 10 * -1);
//		System.out.println(todayEnd.getTime());

		long delayTime = System.currentTimeMillis() + Integer.parseInt("60") * 1000L;
//		System.out.println(delayTime);
//		System.out.println(new Date());
//		System.out.println(new Date(delayTime));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = simpleDateFormat.parse("2023-03-06 13:27:28");
		System.out.println(parse.getTime());
	}

}
