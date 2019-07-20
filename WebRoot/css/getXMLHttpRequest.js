		function createXmlHttpRequest(){
			try{
				return new XMLHttpRequest();		//非IE浏览器
			}catch(e){
				try{
					return new ActiveXObject("Msxml2.XMLHTTP");	//IE6.0
				}catch(e){
					try{
						return new ActiveXObject("Microsoft.XMLHTTP");
					}catch(e){
						alert("您的浏览器版本过低！");
					}
				}
			}
		}