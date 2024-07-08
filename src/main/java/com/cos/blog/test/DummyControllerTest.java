package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

// **html파일이 아니라 data를 리턴**해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired
	private UserRepository userRepository;

	// email, password
	// 스프링부트 강좌 28강(블로그 프로젝트) - update 테스트
	// 업데이트 할때는 ssal을 쓰지 않음
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요.
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());

		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("修正に失敗しました。");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		// userRepository.save(user);
		return null;
	}

	// 스프링부트 강좌 27강(블로그 프로젝트) - 전체 select 및 paging 테스트
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}

	// 스프링부트 강좌 27강(블로그 프로젝트) - 전체 select 및 paging 테스트
	// 한 페이지당 2건의 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(
			@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);

		List<User> users = pagingUser.getContent();
		return users;
	}

	// http://localhost:8080/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {

		//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
		//			@Override
		//			public User get() {
		//				// TODO Auto-generated method stub
		//				return new User();
		//			}
		//		});	
		//		return user;

		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("該当するユーザーがございません。　id : " + id);
			}
		});
		return user;
	}

	@PostMapping("/dummy/join")
	public String join(User user) {
		//		System.out.println("id :::   " + user.getId());
		System.out.println("username :::   " + user.getUsername());
		System.out.println("password :::   " + user.getPassword());
		System.out.println("username :::   " + user.getEmail());
		//		System.out.println("role :::   " + user.getRole());
		//		System.out.println("createDate :::   " + user.getCreateDate());

		user.setRole(RoleType.USER); // ENUM
		userRepository.save(user);
		return "会員登録完了";
	}
} // end class
