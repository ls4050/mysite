# mysite

### versions
1.	mysite02 : mvc, model2, servlet 기반 
2.	mysite03 : spring mvc fw 기반, xml configuration 
3.	mysite04 : spring mvc fw 기반, java configuration 1
4.	mysite05 : spring mvc fw 기반, java configuration 2(web.xml x)
5.	mysite06 : spring boot 기반, java configuration

### errors
1.	Linux IP Address

   vi /etc/sysconfig/network-scripts/ipcfg-{name}
	
	<!-- update -->
	BOOTPROTO="static"
	<!-- add -->
	IPADDR=192.168.0.197
	NETMASK=255.255.255.0
	GATEWAY=192.168.0.1
	DNS1=168.126.63.1
	DNS2=168.126.63.2
	
2.	gateway 확인

   route

3.	DNS 확인
   
   nslookup www.google.com
   
	server=xxx.xxx.xxx -> DNS
   
4.	mysql forward engineering

   인코딩 수정
  
	SET utf8 COLLATE utf8_general_ci;
	
5.	mariaDB 사용자생성, 사용자삭제, 권한조회, 권한부여

	사용자 생성 : create user '[id]'@'[ip]' identified by '[비밀번호]';
	
	사용자 삭제 : drop user '[id]'@'[ip]';
	
	권한 조회 : show grants for '[id]'@'[ip]';
	
	권한 부여 : grant all privileges on [db명].* to '[id]'@'ip';
	
	권한 갱신 : flush privileges;
	
	사용자 전체조회 : select host, user, password from mysql.user;

### 외부 IP 접속
1.	Linux static IP 수정
2.	mariadb 접속 후, 새로운 사용자@ip 생성 및 권한 부여
3.	applicationContext.xml connection pool IP 수정
4.	Jenkins build

### exception controll
![error drawio](https://user-images.githubusercontent.com/66767038/152187615-5ab78bcf-a82f-45a0-9490-b784d94c3de5.png)

### Jenkins
![jenkins drawio](https://user-images.githubusercontent.com/66767038/152201248-10f56ced-7dae-448f-980b-6ed129ec5048.png)

# preventDefault()

```jsx
$("#join-form").submit(function(event){
		event.preventDefault();
	...
}
```

submit이나 a태그를 통한 요청시, 창이 새로고침되는 것을 막아준다

이거 안해줘도 새로고침 안되는데 ??

# Join

## 1. 이메일 다시 입력 시,

```jsx
$("#email").change(function(){
		$("#img-checkemail").hide();
		$("#btn-checkemail").show();
		$("#emailmessage").hide();
	});
```

이메일을 다시 입력한다면, 다시 중복체크를 할 수 있어야한다.

email창의 값이 변할 시, 이미지를 display:none으로 설정하고 btn-checkemail을 보여준다

또한, submit 했을 때 나타났던 validation 메시지는 가려준다.

## 2. 이메일 중복체크

```jsx
$("#btn-checkemail").click(function(){
		var email = $("#email").val();
		if(email == ''){
			return ;
		}
		$.ajax({
			url:"${pageContext.request.contextPath }/user/api/checkemail?email="+email,
			type:"get",
			dataType:"json",
			success:function(response){
				if(response.result !== 'success'){
					console.error(response.message);
					return;
				}
				
				if(response.data) {
					alert("존재하는 이메일입니다. 다른 이메일을 사용해주세요.");
					$("#email")
						.val('')
						.focus();
					return;
				}
				
				$("#img-checkemail").show();
				$("#btn-checkemail").hide();
				
				
			},
			
			error:function(xhr, status, e){
				console.error(status, e);
			}
		});
	});
```

중복체크 버튼을 눌렀을 때, email 에 입력한 값을 ajax를 사용하여 check 하도록한다.

userApiController 로 get방식으로 email을 보내고 이메일에 따른 Uservo를 반환한다.

UserVo가 있다면 중복된 메일이 존재하는 것으로, result(string) = ‘success’, data = true를 반환한다.

UserVo가 없다면 중복된 메일이 없다는 것으로, result = ‘success’, data=false를 반환한다.

중복된 이메일이라면( uservo가 있다면, data=true라면 )  alert를 실행 후, id가 email인 input을 빈칸으로 만들고 커서를 자동으로 올려두게끔 처리한다. (UX Good) → return을 해줌으로서 해당 클릭함수를 종료한다

