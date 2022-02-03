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

	사용자 생성 : create user '[id]'@'[ip]' identified '[비밀번호]';
	사용자 삭제 : drop user '[id]'@'[ip]';
	권한 조회 : show grants for '[id]'@'[ip]';
	권한 부여 : grant all privileges on [db명].* to '[id]'@'ip';
	권한 갱신 : flush privileges;


### 외부 IP 접속
1.	Linux static IP 수정
2.	mariadb 접속 후, 새로운 사용자@ip 생성 및 권한 부여
3.	applicationContext.xml connection pool IP 수정
4.	Jenkins build

### exception controll
![error drawio](https://user-images.githubusercontent.com/66767038/152187615-5ab78bcf-a82f-45a0-9490-b784d94c3de5.png)

### Jenkins
![jenkins drawio](https://user-images.githubusercontent.com/66767038/152201248-10f56ced-7dae-448f-980b-6ed129ec5048.png)

