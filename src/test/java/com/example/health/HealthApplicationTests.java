package com.example.health;

import com.example.health.entity.Address;
import com.example.health.entity.Social;
import com.example.health.entity.User;
import com.example.health.exception.BaseException;
import com.example.health.service.AddressService;
import com.example.health.service.SocialService;
import com.example.health.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthApplicationTests {

	//ใน Test ไม่สามรถทำ Constructer Injection ได้
	@Autowired
	private UserService userService;

	@Autowired
	private SocialService socialService;

	@Autowired
	private AddressService addressService;

	@Order(1)
	@Test
	void testCreate() throws BaseException {
		User user = userService.create(TestCreateData.email, TestCreateData.password, TestCreateData.name);

		//check not null
		Assertions.assertNotNull(user);
		Assertions.assertNotNull(user.getId());


		//check equal
		Assertions.assertEquals(TestCreateData.email,user.getEmail());

		boolean isMatched = userService.matchPassword(TestCreateData.password,user.getPassword());
		Assertions.assertTrue(isMatched);

		Assertions.assertEquals(TestCreateData.name,user.getName());

	}

	@Order(2)
	@Test
	void testUpdate() throws BaseException {
		Optional<User> opt =userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();
		User updatedUser = userService.updateName(user.getId(),TestUpdateData.name);

		Assertions.assertNotNull(updatedUser);

		Assertions.assertEquals(TestUpdateData.name,updatedUser.getName());
	}

	@Order(3)
	@Test
	void testCreateSocial() throws BaseException {
		Optional<User> opt =userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		Social social = user.getSocial();

		Assertions.assertNull(social);

		social = socialService.createSocial(user,
				SocialTestCreateData.facebook,
				SocialTestCreateData.line,
				SocialTestCreateData.instagram,
				SocialTestCreateData.tiktok);

		Assertions.assertNotNull(social);
		Assertions.assertEquals(SocialTestCreateData.facebook,social.getFacebook());

	}

	@Order(4)
	@Test
	void testCreateAddress() throws BaseException {
		Optional<User> opt = userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		List<Address> addresses = user.getAddress();

		Assertions.assertTrue(addresses.isEmpty());

		Address address = addressService.createAddress(user,
				AddressTestCreateData.line1,
				AddressTestCreateData.line2,
				AddressTestCreateData.zipcode);

		Assertions.assertNotNull(address);

		Assertions.assertEquals(AddressTestCreateData.line1,address.getLine1());
		Assertions.assertEquals(AddressTestCreateData.line2,address.getLine2());
		Assertions.assertEquals(AddressTestCreateData.zipcode,address.getZipcode());

		testCreateAddress2(user,AddressTestCreateData2.line1,AddressTestCreateData2.line2,AddressTestCreateData2.zipcode);

	}


	private void testCreateAddress2(User user,String line1,String line2,String zipcode) throws BaseException {

		Address address = addressService.createAddress(user,
				line1,
				line2,
				zipcode);


		Assertions.assertNotNull(address);

		Assertions.assertEquals(line1,address.getLine1());
		Assertions.assertEquals(line2,address.getLine2());
		Assertions.assertEquals(zipcode,address.getZipcode());
	}

	@Order(5)
	@Test
	void testDelete() {

		Optional<User> opt =userService.findByEmail(TestCreateData.email);

		Assertions.assertTrue(opt.isPresent());

		User user = opt.get();

		//Check Social

		Social social = user.getSocial();

		Assertions.assertNotNull(social);
		Assertions.assertEquals(SocialTestCreateData.facebook,social.getFacebook());

		//Check Address

		List<Address> address = user.getAddress();

		Assertions.assertFalse(address.isEmpty());
		Assertions.assertEquals(2, address.size());

		userService.deleteById(user.getId());

		Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
		Assertions.assertTrue(optDelete.isEmpty());

	}

	interface TestCreateData {

		String email = "kazuya.kmatsu@gmail.com";

		String password = "123456789";

		String name = "kazuya komatsu";

	}

	interface SocialTestCreateData {

		String facebook = "Komatsu kazuya";

		String line = "yakungs";

		String instagram= "Yakungs";

		String tiktok = "";

	}


	interface AddressTestCreateData {

		String line1 = "10/95";

		String line2 = "BKK";

		String zipcode = "10520";


	}

	interface AddressTestCreateData2 {

		String line1 = "199/95";

		String line2 = "JAPAN";

		String zipcode = "100";


	}

	interface TestUpdateData{

		String email = "kazuya.komatsu@gmail.com";

		String password = "123456";

		String name = "Kazuya Komatsu";

	}

}
