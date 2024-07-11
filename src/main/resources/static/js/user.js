let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
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


		$.ajax().done().fail();
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
	}
}

index.init(); 