let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			//()=>{} } ::: thisを　bindingする為！！
			this.save();
		});
		$("#btn-login").on("click", () => {
			//()=>{} } ::: thisを　bindingする為！！
			this.login();
		});
	},

	save: function() {
		//		alert('userのsave関数');

		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		// 데이터 값을 로그에 출력
		//		console.log(data);

		$.ajax({
			type: "POST",
			url: "/api/user",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본l적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
		}).done(function(resp) {
			alert("会員登録成功");
			console.log(resp)
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
	},

	login: function() {
				alert('ㅋㅋㅋㅋㅋㅋ');

		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};

		// 데이터 값을 로그에 출력
		console.log(data);

		$.ajax({
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본l적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
		}).done(function(resp) {
			alert("ログイン完了");
			console.log(resp)
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
	}
}

index.init(); 