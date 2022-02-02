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
	IPV6_ADDR=192.168.0.197
	NETMASK=255.255.255.0
	GATEWAY=192.168.0.1
	DNS=168.126.63.1
	
### exception controll
![error drawio](https://user-images.githubusercontent.com/66767038/152187615-5ab78bcf-a82f-45a0-9490-b784d94c3de5.png)

### 외부 IP 접속
1.	Linux static IP 수정
2.	applicationContext.xml connection pool IP 수정 
3.	Jenkins build

