# iGov.ua APIs
<b>Авто-доки:</b>
- <a href="https://jenkins.igov.org.ua//view/new/job/central_alpha/ws/wf-base/target/site/apidocs/index.html">wf-base</a>
- <a href="https://jenkins.igov.org.ua//view/new/job/central_alpha/ws/wf-central/target/site/apidocs/index.html">wf-central</a>

<b>Авто-доки. Swagger online:</b>
- https://test.igov.org.ua/wf/service/api-docs/swagger-ui.html
- https://test.region.igov.org.ua/wf/service/api-docs/swagger-ui.html

<b>Авто-доки. Swagger static-docs документация: (не доступны во время сборки от этапа clean до verify )</b>
- [wf-base ( igov )](https://jenkins-new.igov.org.ua/job/central_alpha/ws/wf-base/target/generated-docs/base-igov.html) 
- [wf-base( igov + Activiti )](https://jenkins-new.igov.org.ua/job/central_alpha/ws/wf-base/target/generated-docs/base-default.html)
- [wf-central ( igov )](https://jenkins-new.igov.org.ua/job/central_alpha/ws/wf-central/target/generated-docs/central-igov.html)
- [wf-central ( igov + Activiti )](https://jenkins-new.igov.org.ua/job/central_alpha/ws/wf-central/target/generated-docs/central-default.html)

<b>Подробное описание:</b>
<a name="0_contents">*Contents*</a><br/>
<a href="#1">1. Аутентификация пользователя (описание занесено в Swagger) </a><br/>
<a href="#2">2. Activiti (описание занесено в Swagger ) </a><br/>
<a href="#6_loadFileFromDb">6. Загрузки прикрепленного к заявке файла из постоянной базы (описание занесено в Swagger) </a><br/>
<a href="#7_workWithMerchants">7. Работа с мерчантами (описание занесено в Swagger) </a><br/>
<a href="#8_workWithTables">8. Бэкап/восстановление данных таблиц сервисов и мест  (описание занесено в Swagger)</a><br/>
<a href="#9_workWithDocuments">9. Работа с документами (описание занесено в Swagger)</a><br/>
<a href="#10_workWithSubjects">10. Работа с субъектами (описание занесено в Swagger)</a><br/>
<a href="#11_accessDocuments">11. Предоставление и проверка доступа к документам (описание занесено в Swagger)</a><br/>
<a href="#12_workWithMessages">12. Работа с сообщениями (описание занесено в Swagger)</a><br/>
<a href="#13_workWithHistoryEvents">13. Работа с историей (Мой журнал)  (описание занесено в Swagger) </a><br/>
<a href="#14_uploadFileToDb">14. Аплоад(upload) и прикрепление файла в виде атачмента к таске Activiti (описание занесено в Swagger)</a><br/>
<a href="#15_workWithServices">15. Работа с каталогом сервисов (описание занесено в Swagger)</a><br/>
<a href="#16_getWorkflowStatistics">16. Получение статистики по задачам в рамках бизнес процесса (описание занесено в Swagger)</a><br/>
<a href="#17_workWithHistoryEvent_Services">17. Работа с обьектами событий по услугам (описание занесено в Swagger)</a><br/>
<a href="#18_workWithFlowSlot">18. Электронные очереди (слоты потока, расписания и тикеты) (описание занесено в Swagger)</a><br/>
<a href="#19">19. Работа с джоинами суьтектами (отделениями/филиалами) (описание занесено в Swagger)</a><br/>
<a href="#20">20. Получение кнопки для оплаты через Liqpay (описание занесено в Swagger)</a><br/>
<a href="#21">21. Работа со странами  (описание занесено в Swagger)</a><br/>
<a href="#22">22. Загрузка данных по задачам (описание занесено в Swagger) </a><br/>
<a href="#23_getBPForUsers"> 23. Получение списка бизнес процессов к которым у пользователя есть доступ (описание занесено в Swagger) </a><br/>
<a href="#24_getSheduleFlowIncludes"> 24. Получение расписаний включений (описание занесено в Swagger)</a><br/>
<a href="#25_setSheduleFlowInclude"> 25. Добавление/изменение расписания включений (описание занесено в Swagger)</a><br/>
<a href="#26_removeSheduleFlowInclude"> 26. Удаление расписания включений (описание занесено в Swagger)</a><br/>
<a href="#27_getSheduleFlowExcludes"> 27. Получение расписаний исключений (описание занесено в Swagger)</a><br/>
<a href="#28_setSheduleFlowExclude"> 28. Добавление/изменение расписания исключения (описание занесено в Swagger)</a><br/>
<a href="#29_removeSheduleFlowExclude"> 29. Удаление расписания исключений (описание занесено в Swagger)</a><br/>
<a href="#30_workWithPatternFiles"> 30. Работа с файлами-шаблонами (описание занесено в Swagger) </a><br/>
<a href="#31_getFlowSlotTickets"> 31. Получение активных тикетов (описание занесено в Swagger)</a><br/>
<a href="#32_getTasksByOrder"> 32. Получение списка ID пользовательских тасок по номеру заявки  (описание занесено в Swagger) </a><br/>
<a href="#33_getStatisticServiceCounts"> 33. Получение количества записей HistoryEvent_Service для сервиса по регионам  (описание занесено в Swagger) </a><br/>
<a href="#34_upload_content_as_attach">34. Аплоад(upload) и прикрепление текстовго файла в виде атачмента к таске Activiti (описание занесено в Swagger) </a><br/>
<a href="#35">35. Электронная эскалация (описание занесено в Swagger) </a><br/>
<a href="#36_getTasksByText">36. Поиск заявок по тексту (в значениях полей без учета регистра)  (описание занесено в Swagger) </a><br/> 
<a href="#37_getAccessKeyt">37. Получения ключа для аутентификации (описание занесено в Swagger) </a><br/> 
<a href="#38_setTaskQuestions">38. Вызов сервиса уточнения полей формы  (описание занесено в Swagger) </a><br/> 
<a href="#39_setTaskAnswer">39. Вызов сервиса ответа по полям требующим уточнения (описание занесено в Swagger) </a><br/> 
<a href="#40_AccessServiceLoginRight">40. Получение и установка прав доступа к rest сервисам  (описание занесено в Swagger) </a><br/> 
<a href="#41_getFlowSlots_Department">41. Получение массива объектов SubjectOrganDepartment по ID бизнес процесса (описание занесено в Swagger)</a><br/> 
<a href="#42_getPlace">42. Работа с универсальной сущностью Place (области, районы, города, деревни) (описание занесено в Swagger) </a><br/> 
<a href="#43_check_attachment_sign">43. Проверка ЭЦП на атачменте(файл) таски Activiti (описание занесено в Swagger)</a><br/> 
<a href="#44_check_file_from_redis_sign">44. Проверка ЭЦП на файле хранящемся в Redis (описание занесено в Swagger) </a><br/>
<a href="#45_getServer">45. Получение информации о сервере (описание занесено в Swagger) </a><br/>
<a href="#46_getLastTaskHistory">46. Проверка наличия task определенного Бизнес процесса (БП), указанного гражданина (описание занесено в Swagger) </a><br/>
<a href="#47_getStartFormByTask">47. Получение полей стартовой формы по: ИД субьекта, ИД услуги, ИД места Услуги.  (описание занесено в Swagger) </a><br/>
<a href="#48_getStartFormData">48. Получение полей стартовой формы по ID таски. (описание занесено в Swagger)</a><br/>
<a href="#49">49. Субьекты-органы - Филиалы - Таможенные (описание занесено в Swagger)</a><br/>
<a href="#50">50. Работа с валютами  (описание занесено в Swagger)</a><br/>


## iGov.ua APIs

##### Mandatory HTTP Headers

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/json |
| Accept | application/json |
| Authorization | Basic ... |
--------------------------------------------------------------------------------------------------------------------------
**Как проверить работоспособность запроса:**

Для этого можно воспользоваться программой Fiddler http://www.telerik.com/fiddler или аналогами.<br/>
В ней есть вкладка Composer с помощью которой можно отправлять запросы к API.<br/>
Нужно указать три обязательных заголовка:<br/>
```text
Content-Type:  application/json
Accept: application/json
Authorization: Basic [login:password] закодированные в формате base64 
```
Чтобы закодировать login:password в формат base64 можно воспользоваться онлайн генератором: https://www.base64encode.org/
<br/>
Также можно ловить запросы отправляемые node.js на сервер. Вот тут описано как: http://stackoverflow.com/questions/17383351/how-to-capture-http-messages-from-request-node-library-with-fiddler

--------------------------------------------------------------------------------------------------------------------------

<a name="1">
<a href="#1"><h3>1. Аутентификация пользователя</h3></a>
<br/>

<a href="#1"><h4>Логин пользователя:</h4></a>
<a href="#0_contents">↑Up</a>

**HTTP Metod: POST**

**HTTP Context: https://server:port/wf/service/access/login**

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/x-www-form-urlencoded |

* sLogin - Логин пользователя
* sPassword - Пароль пользователя

**Request:**

```text
    sLogin=user&sPassword=password
```

**Response:**

```json
	{"session":"true"}
```
где:<br/>
true - Пользователь авторизирован   
false - Имя пользователя или пароль не корректны

<br/>

<a href="#1"><h4>Логаут пользователя (наличие cookie JSESSIONID):</h4></a>
<a href="#0_contents">↑Up</a>

**HTTP Metod: POST/DELETE**

**HTTP Context: https://server:port/wf/service/auth/logout**

**Response:**

```json
	{"session":"97AE7CA414A5DA85749FE379CC843796"}
```
--------------------------------------------------------------------------------------------------------------------------

<a name="2">
<a href="#2"><h3>2. Activiti (описание занесено в Swagger)</h3></a>
<br/>

<a href="#2"><h4>Запуск процесса Activiti (описание занесено в Swagger):</h4></a>
<a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/action/task/start-process/{key}**

* key - Ключ процесса
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**Request:**

https://test.region.igov.org.ua/wf/service/action/task/start-process/citizensRequest

**Response:**

```json
	{
		"id":"31"
	}
```

<br/>

<a href="#2"><h4>Загрузка задач из Activiti:  (описание занесено в Swagger) </h4></a>
<a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/action/task/{assignee}** 

* assignee - Владелец
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**Request:**

https://test.region.igov.org.ua/wf/service/action/task/kermit

**Response:**

```json
	[                                                
  	  {
    		"delegationState": "RESOLVED",             
		    "id": "38",                                  
		    "name": "Первый процесс пользователя kermit",
		    "description": "Описание процесса",           
		    "priority": 51,                               
		    "owner": "kermit-owner",                      
		    "assignee": "kermit-assignee",                
		    "processInstanceId": "12",                   
		    "executionId": "1",                           
		    "createTime": "2015-04-13 00:51:34.527",      
		    "taskDefinitionKey": "task-definition",       
		    "dueDate": "2015-04-13 00:51:36.527",        
		    "category": "my-category",                    
		    "parentTaskId": "2",                          
		    "tenantId": "diver",                          
		    "formKey": "form-key-12",                     
		    "suspended": true,                            
		    "processDefinitionId": "21"                   
	  }
	]
```

<br/>

<a href="#2"><h4>Загрузка каталога сервисов из Activiti: (описание занесено в Swagger) </h4></a>
<a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/action/task/process-definitions**

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**Request:**

https://test.region.igov.org.ua/wf/service/action/task/process-definitions

**Response:**

```json
	[											                            
          {
    		"id": "CivilCardAccountlRequest:1:9",                            
		    "category": "http://www.activiti.org/test",                       
		    "name": "Видача картки обліку об’єкта торговельного призначення", 
		    "key": "CivilCardAccountlRequest",                                
		    "description": "Описание процесса",                               
		    "version": 1,                                                     
		    "resourceName": "dnepr-2.bpmn",                                   
		    "deploymentId": "1",                                            
		    "diagramResourceName": "dnepr-2.CivilCardAccountlRequest.png",   
		    "tenantId": "diver",                                              
		    "suspended": true                                                 
	   }
	]
```

--------------------------------------------------------------------------------------------------------------------------

<a name="6_loadFileFromDb">
####6. Загрузки прикрепленного к заявке файла из постоянной базы (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/action/task/download_file_from_db?taskId=XXX&attachmentId=XXX&nFile=XXX**

* {taskId} - ид задачи
* {attachmentID} - ID прикрепленного файла
* {nFile} - порядковый номер прикрепленного файла
* {nID_Subject} - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/object/file/download_file_from_db?taskId=82596&attachmentId=6726532&nFile=7


<a name="7_workWithMerchants">
####7. Работа с мерчантами (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/finance/getMerchants** - получить весь список обьектов мерчантов

**Response**

```json					
	[
		{
			"nID":1
			,"sID":"Test_sID"
			,"sName":"Test_sName"
			,"sPrivateKey":"test_sPrivateKey"
			,"sURL_CallbackStatusNew":"test_sURL_CallbackStatusNew"
			,"sURL_CallbackPaySuccess":"test_sURL_CallbackPaySuccess"
			,"nID_SubjectOrgan":1
		}
		,{
			"nID":2
			,"sID":"i10172968078"
			,"sName":"igov test"
			,"sPrivateKey":"BStHb3EMmVSYefW2ejwJYz0CY6rDVMj1ZugJdZ2K"
			,"sURL_CallbackStatusNew":"test_sURL_CallbackStatusNew"
			,"sURL_CallbackPaySuccess":"test_sURL_CallbackPaySuccess"
			,"nID_SubjectOrgan":1
		}
	]	
```

Пример:
https://test.igov.org.ua/wf/service/finance/getMerchants



**HTTP Metod: GET**
**HTTP Context: https://server:port/wf/service/finance/getMerchant** - получить обьект мерчанта

* sID - ID-строка мерчанта(публичный ключ)

**Response**

```json	
	{
		"nID":1
		,"sID":"Test_sID"
		,"sName":"Test_sName"
		,"sPrivateKey":"test_sPrivateKey"
		,"sURL_CallbackStatusNew":"test_sURL_CallbackStatusNew"
		,"sURL_CallbackPaySuccess":"test_sURL_CallbackPaySuccess"
		,"nID_SubjectOrgan":1
	}
```

Пример:
https://test.igov.org.ua/wf/service/finance/getMerchant?sID=i10172968078



**HTTP Metod: DELETE**

**HTTP Context: http://server:port/wf/service/finance/removeMerchant** - удалить мерчанта

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/x-www-form-urlencoded |

* sID - ID-строка мерчанта(публичный ключ)

**Response**

``` Status 200 ```

Пример:
https://test.igov.org.ua/wf/service/finance/removeMerchant?sID=i10172968078




**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/finance/setMerchant** - обновить информацию мерчанта

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/x-www-form-urlencoded |

* nID - ID-номер мерчанта(внутренний) //опциональный (если не задан или не найден - будет добавлена запись)
* sID - ID-строка мерчанта(публичный ключ) //опциональный (если не задан или не найден - будет добавлена запись)
* sName - строковое название мерчанта //опциональный (при добавлении записи - обязательный)
* sPrivateKey - приватный ключ мерчанта //опциональный (при добавлении записи - обязательный)
* nID_SubjectOrgan - ID-номер субьекта-органа мерчанта(может быть общий субьект у нескольких мерчантов) //опциональный
* sURL_CallbackStatusNew - строка-URL каллбэка, при новом статусе платежа(проведении проплаты) //опциональный
* sURL_CallbackPaySuccess - строка-URL каллбэка, после успешной отправки платежа //опциональный

**Response**

```json	
	{
		"nID":1
		,"sID":"Test_sID"
		,"sName":"Test_sName22"
		,"sPrivateKey":"test_sPrivateKey"
		,"sURL_CallbackStatusNew":"test_sURL_CallbackStatusNew"
		,"sURL_CallbackPaySuccess":"test_sURL_CallbackPaySuccess"
		,"nID_SubjectOrgan":1
	}
```

Примеры обновления:
https://test.igov.org.ua/wf/service/finance/setMerchant?sID=Test_sID&sName=Test_sName2
https://test.igov.org.ua/wf/service/finance/setMerchant?nID=1&sName=Test_sName22
Пример добавления:
https://test.igov.org.ua/wf/service/finance/setMerchant?sID=Test_sID3&sName=Test_sName3&sPrivateKey=121212 





<a name="8_workWithTables">
####8. Бэкап/восстановление данных таблиц сервисов и мест  (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/action/item/getServicesAndPlacesTables** - Скачать данные в виде json  (описание занесено в Swagger)

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/json |

 * nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)
 

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/action/item/downloadServicesAndPlacesTables** - Скачать данные в json файле  (описание занесено в Swagger)

| Name        | Value           |
| ------------- |:-------------:|
| Content-Disposition | attachment |

 * nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/action/item/setServicesAndPlacesTables** - Загрузить в виде json (в теле POST запроса)  (описание занесено в Swagger)

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/json |

 * nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/action/item/uploadServicesAndPlacesTables** - Загрузить из json файла (описание занесено в Swagger)

| Name        | Value           |
| ------------- |:-------------:|
| Content-Type | application/json |

 * nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример страницы формы загрузки из файла:

&lt;html&gt;<br/>&lt;body&gt;<br/>&lt;form method=&quot;POST&quot; enctype=&quot;multipart/form-data&quot;<br/>action=&quot;http://localhost:8080/wf/service/action/item/uploadServicesAndPlacesTables&quot;&gt;<br/>File to upload: &lt;input type=&quot;file&quot; name=&quot;file&quot;&gt;&lt;br /&gt; &lt;input type=&quot;submit&quot;<br/>value=&quot;Upload&quot;&gt; Press here to upload the file!<br/>&lt;/form&gt;<br/>&lt;/body&gt;<br/>&lt;/html&gt;


----------------------------------------------------------------------------------------------------------------------------

<a name="9_workWithDocuments">
####9. Работа с документами (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocument** - получение документа по ид документа (описание занесено в Swagger)

* nID - ИД-номер документа
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/document/getDocument?nID=1

**Response**
```json
{
	"sDate_Upload":"2015-01-01",
	"sContentType":"text/plain",
	"contentType":"text/plain",
	"nID":1,
	"sName":"Паспорт",
	"oDocumentType":{"nID":0,"sName":"Другое"},
	"sID_Content":"1",
	"oDocumentContentType":{"nID":2,"sName":"text/plain"},
	"sFile":"dd.txt",
	"oDate_Upload":1420063200000,
	"sID_Subject_Upload":"1",
	"sSubjectName_Upload":"ПриватБанк",
	"oSubject_Upload":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк", "sLabelShort":"ПриватБанк"},
	 "oSubject":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк","sLabelShort":"ПриватБанк"}
 }
```


----------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocumentContent** - получение контента документа по ид документа (описание занесено в Swagger)

* nID - ИД-номер документа
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/document/getDocumentContent?nID=1

**Response**
КОНТЕНТ ДОКУМЕНТА В ВИДЕ СТРОКИ


----------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocumentFile** - получение документа в виде файла по ид документа (описание занесено в Swagger)

* nID - ИД-номер документа
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/document/getDocumentFile?nID=1

**Response**
ЗАГРУЖЕННЫЙ ФАЙЛ 

----------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocumentAbstract** - получение документа в виде файла (описание занесено в Swagger)

* sID - строковой ID документа (параметр обязателен)
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя) (параметр опционален)
* nID_DocumentOperator_SubjectOrgan - определяет класс хэндлера который будет обрабатывать запрос (параметр опционален)
* nID_DocumentType - определяет тип документа, например 0 - "Квитанція про сплату", 1 - "Довідка про рух по картці (для візових центрів)" (параметр опционален)
* sPass - пароль (параметр опционален)

Пример:
https://test.igov.org.ua/wf/service/document/getDocumentAbstract?sID=150826SV7733A36E803B

**Response**
ЗАГРУЖЕННЫЙ ФАЙЛ

----------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocuments** - получение списка загруженных субъектом документов (описание занесено в Swagger)

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/document/getDocuments?nID_Subject=2

**Response**
```json
[
	{
	"sDate_Upload":"2015-01-01",
	"sContentType":"text/plain",
	"contentType":"text/plain",
	"nID":1,
	"sName":"Паспорт",
	"oDocumentType":{"nID":0,"sName":"Другое"},
	"sID_Content":"1",
	"oDocumentContentType":{"nID":2,"sName":"text/plain"},
	"sFile":"dd.txt",
	"oDate_Upload":1420063200000,
	"sID_Subject_Upload":"1",
	"sSubjectName_Upload":"ПриватБанк",
	"oSubject_Upload":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк", "sLabelShort":"ПриватБанк"},
	 "oSubject":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк","sLabelShort":"ПриватБанк"}
         },
         {
	"sDate_Upload":"2015-01-01",
	"sContentType":"text/plain",
	"contentType":"text/plain",
	"nID":2,
	"sName":"Паспорт",
	"oDocumentType":{"nID":0,"sName":"Другое"},
	"sID_Content":"2",
	"oDocumentContentType":{"nID":2,"sName":"text/plain"},
	"sFile":"dd.txt",
	"oDate_Upload":1420063200000,
	"sID_Subject_Upload":"1",
	"sSubjectName_Upload":"ПриватБанк",
	"oSubject_Upload":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк", "sLabelShort":"ПриватБанк"},
	 "oSubject":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк","sLabelShort":"ПриватБанк"}
         }
]
```
 ---------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/document/setDocument** - сохранение документа (описание занесено в Swagger)

* sID_Subject_Upload - ИД-строка субъекта, который загрузил документ
* sSubjectName_Upload - строка-название субъекта, который загрузил документ (временный парметр, будет убран)
* sName - строка-название документа
* sFile - строка-название и расширение файла
* nID_DocumentType - ИД-номер типа документа
* sDocumentContentType - строка-тип контента документа
* soDocumentContent - контект в виде строки-обьекта
* nID_Subject - ИД-номер субъекта документа (владельца) ????????????????????????????????????

Пример:
https://test.igov.org.ua/wf/service/document/setDocument?sID_Subject_Upload=123&sSubjectName_Upload=Vasia&sName=Pasport&sFile=file.txt&nID_DocumentType=1&sDocumentContentType=application/zip&soDocumentContent=ffffffffffffffffff&nID_Subject=1

**Response**
ИД ДОКУМЕНТА

--------------------------------------------------------------------------------------------------------------------------
 
**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/document/setDocumentFile** - сохранение документа в виде файла (описание занесено в Swagger)
(контент файла шлется в теле запроса)

* sID_Subject_Upload - ИД-строка субъекта, который загрузил документ
* sSubjectName_Upload - строка-название субъекта, который загрузил документ (временный парметр, нужно убрать его)
* sName - строка-название документа
* nID_DocumentType - ИД-номер типа документа
* sDocumentContentType - строка-тип контента документа
* soDocumentContent - контент в виде строки-обьекта
* nID_Subject - ИД-номер субъекта документа (владельца)????????????????????????????????????
* oFile - обьект файла (тип MultipartFile)


**Response**
ИД ДОКУМЕНТА

----------------------------------------------------------------------------------------------------------------------------
 ТИПЫ ДОКУМЕНТОВ

----------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocumentTypes**
 - получение списка всех "нескрытых" типов документов, т.е. у которых поле bHidden=false (описание занесено в Swagger)

Пример:
https://test.igov.org.ua/wf/service/document/getDocumentTypes

**Response**
```json
[
	{"nID":0,"sName":"Другое", "bHidden":false},
	{"nID":1,"sName":"Справка", "bHidden":false},
	{"nID":2,"sName":"Паспорт", "bHidden":false}
]
```

--------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/setDocumentType** - добавить/изменить запись типа документа (описание занесено в Swagger)
параметры:

 * nID -- ид записи (число)
 * sName -- название записи (строка)
 * bHidden -- скрывать/не скрывать (при отдаче списка всех записей, булевское, по умолчанию = false)

 Если запись с ид=nID не будет найдена, то создастся новая запись (с автогенерируемым nID), иначе -- обновится текущая.
 
  примеры:
  
создать новый тип:
https://test.igov.org.ua/wf/service/document/setDocumentType?nID=100&sName=test

ответ: ```{"nID":20314,"sName":"test", , "bHidden":false}```

изменить (взять ид из предыдущего ответа):
https://test.igov.org.ua/wf/service/document/setDocumentType?nID=20314&sName=test2

ответ: ```{"nID":20314,"sName":"test2", "bHidden":false}```

--------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/removeDocumentType** - удаление записи по ее ид (описание занесено в Swagger)
параметры:
 *nID -- ид записи

  Если запись с ид=nID не будет найдена, то вернется ошибка *403. Record not found*, иначе -- запись удалится.

пример:
https://test.igov.org.ua/wf/service/document/removeDocumentType?nID=20314

ответ: ```200 ok ```

--------------------------------------------------------------------------------------------------------------------------
 ТИПЫ КОНТЕНТА ДОКУМЕНТОВ

----------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocumentContentTypes** - получение списка типов контента документов (описание занесено в Swagger)

Пример:
https://test.igov.org.ua/wf/service/document/getDocumentContentTypes

**Response**
```json
[
	{"nID":0,"sName":"application/json"},
	{"nID":1,"sName":"application/xml"},
	{"nID":2,"sName":"text/plain"},
	{"nID":3,"sName":"application/jpg"}
]
```

--------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/setDocumentContentType** - добавить/изменить запись типа контента документа (описание занесено в Swagger)
параметры:

 *nID -- ид записи

 *sName -- название записи

 Если запись с ид=nID не будет найдена, то создастся новая запись (с автогенерируемым nID), иначе -- обновится текущая.
 
  примеры:
  
создать новый тип:
https://test.igov.org.ua/wf/service/document/setDocumentContentType?nID=100&sName=test

ответ: ```{"nID":20311,"sName":"test"}```

изменить (взять ид из предыдущего ответа):
https://test.igov.org.ua/wf/service/document/setDocumentContentType?nID=20311&sName=test2

ответ: ``` {"nID":20311,"sName":"test2"}```

--------------------------------------------------------------------------------------------------------------------------
**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/removeDocumentContentType** - удаление записи по ее ид (описание занесено в Swagger)
параметры:
 *nID -- ид записи

  Если запись с ид=nID не будет найдена, то вернется ошибка *403. Record not found*, иначе -- запись удалится.

пример:
https://test.igov.org.ua/wf/service/document/removeDocumentContentType?nID=20311

ответ: ```200 ok ```

--------------------------------------------------------------------------------------------------------------------------


<a name="10_workWithSubjects">
####10. Работа с субъектами (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/subject/syncSubject** - получение субъекта, если таков найден, или добавление субъекта в противном случае

От клиента ожидается ОДИН и только ОДИН параметр из нижеперечисленных

* nID - ИД-номер субъекта
* sINN - строка-ИНН (субъект - человек)
* sOKPO - строка-ОКПО (субъек - организация)
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Примеры:

https://test.igov.org.ua/wf/service/subject/syncSubject?sINN=34125265377

https://test.igov.org.ua/wf/service/subject/syncSubject?sOKPO=123

https://test.igov.org.ua/wf/service/subject/syncSubject?nID=1

**Response**
```json
{
	"nID":150,
	"sID":"34125265377",
	"sLabel":null,
	"sLabelShort":null
}
```
--------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/document/getDocumentOperators** - получение всех операторов(органов) которые имею право доступа к документу (описание занесено в Swagger)

Примеры: https://test.igov.org.ua/wf/service/document/getDocumentOperators

**Response**
```json
[
    {
        "nID_SubjectOrgan": 2,
        "sHandlerClass": "org.igov.activiti.common.document.DocumentAccessHandler_IGov",
        "nID": 1,
        "sName": "iGov"
    }
]
```
--------------------------------------------------------------------------------------------------------------------------

<a name="11_accessDocuments">
####11. Предоставление и проверка доступа к документам (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: POST**

**HTTP Context: 
https://seriver:port/wf/service/document/access/setDocumentLink - запись на доступ, с генерацией и получением уникальной ссылки на него

* nID_Document - ИД-номер документа
* sFIO - ФИО, кому доступ
* sTarget - цель получения доступа
* sTelephone - телефон того, кому доступ предоставляется
* nDays - число милисекунд, на которое предоставляется доступ
* sMail - эл. почта того, кому доступ предоставляется
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**Response**

```json					
	[				        //[0..N]
	{"name":"sURL",   //[1..1]
	 "value":"https://e-gov.org.ua/index#nID_Access=4345&sSecret=JHg3987JHg3987JHg3987" //[1..1]
	}  
	]
```



<a name="12_workWithMessages">
####12. Работа с сообщениями (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/subject/message/getMessage** - получение массива сообщений

Примеры:

https://test.igov.org.ua/wf/service/subject/message/getMessages

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Response:
```json
[
	{
		"nID":76,"sHead":"Закликаю владу перевести цю послугу в електронну форму!"
		,"sBody":"Дніпропетровськ - Видача витягу з технічної документації про нормативну грошову оцінку земельної ділянки"
		,"sDate":"2015-06-03 22:09:16.536"
		,"nID_Subject":0
		,"sMail":"bvv4ik@gmail.com"
		,"sContacts":""
		,"sData":""
		,"oSubjectMessageType": {
			"sDescription": "Просьба добавить услугу",
			"nID": 0,
			"sName": "ServiceNeed"
		}
	}
]
```

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/subject/message/getMessage** - получение сообщения

* nID - ИД-номер сообщения

Примеры:
https://test.igov.org.ua/wf/service/subject/message/getMessage?nID=76

* nID - ID сообщения

Ответ:
```json
{
	"nID":76
	,"sHead":"Закликаю владу перевести цю послугу в електронну форму!"
	,"sBody":"Дніпропетровськ - Видача витягу з технічної документації про нормативну грошову оцінку земельної ділянки"
	,"sDate":"2015-06-03 22:09:16.536"
	,"nID_Subject":0
	,"sMail":"bvv4ik@gmail.com"
	,"sContacts":""
	,"sData":""
	,"oSubjectMessageType": {
		"sDescription": "Просьба добавить услугу",
		"nID": 0,
		"sName": "ServiceNeed"
	}
}
```
**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/subject/message/setMessage** - сохранение сообщения

* sHead - Строка-заглавие сообщения
* sBody - Строка-тело сообщения
* nID_Subject ИД-номер субьекта (автора) //опционально (добавляется в запрос автоматически после аутентификации пользователя)
* sMail - Строка электронного адреса автора //опционально
* sContacts - Строка контактов автора //опционально
* sData - Строка дополнительных данных автора //опционально
* nID_SubjectMessageType - ИД-номер типа сообщения  //опционально (по умолчанию == 0) 
* sID_Order -- строка-ид заявки (опционально)
* nID_Protected - номер заявки, опционально, защищенный по алгоритму Луна
* nID_Server -- ид сервера, где расположена заявка (опционально, по умолчанию 0)
* sID_Rate -- оценка, опционально. сейчас должно содержать число от 1 до 5

nID_SubjectMessageType:
nID;sName;sDescription
0;ServiceNeed;Просьба добавить услугу
1;ServiceFeedback;Отзыв о услуге

При заданных параметрах sID\_Order или nID\_Protected с/без nID_Server и sID\_Rate - обновляется поле nRate в записи сущности HistoryEvent\_Service, 
которая находится по sID\_Order или nID\_Protected с/без nID_Server (подробнее [тут](#17_workWithHistoryEvent_Services), 
при этом приходящее значение из параметра sID_Rate должно содержать число от 1 до 5.
т.е. возможные ошибки:
 - nID\_Protected некорректное -- ошибка ```403. CRC Error```, пишется в лог (т.е. сообщение все равно сохраняется)
 - sID\_Rate некорректное (не число или не в промежутке от 1 до 5) -- ошибка ```403. Incorrect sID_Rate```, пишется в лог
 - запись заявки (по nID\_Protected без последней цифры) не найдена -- ошибка ```403. Record not found```, пишется в лог
проверить запись HistoryEvent\_Service можно через сервис \sevices\getHistoryEvent_Service?nID_Protected=xxx (link: <a href="#17_workWithHistoryEvent_Services">17. Работа с обьектами событий по услугам</a>)


Примеры:
https://test.igov.org.ua/wf/service/subject/message/setMessage?sHead=name&sBody=body&sMail=a@a.a

Ответ:
 Status 200 если Ok

--------------------------------------------------------------------------------------------------------------------------


<a name="13_workWithHistoryEvents">
####13. Работа с историей (Мой журнал)  (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/action/event/getHistoryEvent** - получение документа по ид документа

* nID - ИД-номер документа
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/action/event/getHistoryEvent?nID=1

----------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/action/event/getHistoryEvents** - загрузка событий  (описание занесено в Swagger) 

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)????????

Пример:
https://test.igov.org.ua/wf/service/action/event/getHistoryEvents?nID_Subject=3

 ---------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/action/event/setHistoryEvent** - сохранение события  (описание занесено в Swagger) 

* nID_Subject - ИД-строка субъекта, который загрузил документ (необязательное поле)???????????????????????????????????
* nID_HistoryEventType - ИД-номер типа документа (необязательное поле)
* sEventName - строка - кастомное описание документа (необязательное поле)
* sMessage - строка - сохраняемое содержимое (обязательное поле)

--------------------------------------------------------------------------------------------------------------------------

<a name="14_uploadFileToDb">
####14. Аплоад(upload) и прикрепление файла в виде атачмента к таске Activiti (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/object/file/upload_file_as_attachment** - Аплоад(upload) и прикрепление файла в виде атачмента к таске Activiti

* taskId - ИД-номер таски
* description - описание
* file - в html это имя элемента input типа file - <input name="file" type="file" />. в HTTP заголовках - Content-Disposition: form-data; name="file" ...
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
http://test.igov.org.ua/wf/service/object/file/upload_file_as_attachment?taskId=68&description=ololo"

Ответ без ошибок:
```json
{"taskId":"38","processInstanceId":null,"userId":"kermit","name":"jmt.png","id":"45","type":"image/png;png","description":"SomeDocumentDescription","time":1433539278957,"url":null} 
ID созданного attachment - "id":"45"
```
Ответ с ошибкой:
```json
{"code":"SYSTEM_ERR","message":"Cannot find task with id 384"}
```

--------------------------------------------------------------------------------------------------------------------------

<a name="15_workWithServices">
####15. Работа с каталогом сервисов  (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Context: http://server:port/wf/service/action/item/getServicesTree** - Получение дерева сервисов  (описание занесено в Swagger)

**HTTP Metod: GET**

* sFind - фильтр по имени сервиса (не обязательный параметр). Если задано, то производится фильтрация данных - возвращаются только сервиса в имени которых встречается значение этого параметра, без учета регистра.
* asID_Place_UA - фильтр по ID места (мест), где надается услуга. Поддерживаемие ID: 3200000000 (КИЇВСЬКА ОБЛАСТЬ/М.КИЇВ), 8000000000 (М.КИЇВ). Если указан другой ID, фильтр не применяется.
* bShowEmptyFolders - Возвращать или нет пустые категории и подкатегории (опциональный, по умолчанию false)
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

**Дополнительно:**

Если general.bTest = false, сервисы, имя которых начинается с "_", не вовращаются.

Пример:
https://test.igov.org.ua/wf/service/action/item/getServicesTree?asID_Place_UA=3200000000,8000000000

Ответ:
```json
[{"nID":1,"sID":"Citizen","sName":"Громадянам","nOrder":1,"aSubcategory":[{"nID":1,"sName":"Будівництво, нерухомість, земля","sID":"Build","nOrder":1,"aService":[{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":6,"sName":"Видача відомостей з документації, що включена до місцевого фонду документації із землеустрою.","nOrder":6,"nSub":1},{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":8,"sName":"Надання довідки про перебування на квартирному обліку при міськвиконкомі за місцем проживання та в житлово-будівельному кооперативі.","nOrder":8,"nSub":1},{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":9,"sName":"Надання довідки про перебування на обліку бажаючих отримати земельну ділянку під індивідуальне будівництво","nOrder":9,"nSub":0},{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":10,"sName":"Видача витягу з технічної документації про нормативну грошову оцінку земельної ділянки","nOrder":10,"nSub":2},{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":11,"sName":"Надання відомостей з Державного земельного кадастру у формі витягу з Державного земельного кадастру про земельну ділянку","nOrder":11,"nSub":0},{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":12,"sName":"Присвоєння поштової адреси об’єкту нерухомого майна","nOrder":12,"nSub":1},{"sSubjectOperatorName":"Міська Рада","subjectOperatorName":"Міська Рада","nID":13,"sName":"Видача довідок про перебування на квартирному обліку","nOrder":13,"nSub":0}]
```

**HTTP Context: http://server:port/wf/service/action/item/getService** - Получение сервиса

**HTTP Metod: GET**

* nID - ИД-номер сервиса
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/action/item/getService?nID=1

Ответ:
```json
{"sSubjectOperatorName":"МВС","subjectOperatorName":"МВС","nID":1,"sName":"Отримати довідку про несудимість","nOrder":1,"aServiceData":[{"nID":1,"nID_City":{"nID":2,"sName":"Кривий Ріг","nID_Region":{"nID":1,"sName":"Дніпропетровська"}},"nID_ServiceType":{"nID":1,"sName":"Внешняя","sNote":"Пользователь переходит по ссылке на услугу, реализованную на сторонней платформе"},"oSubject_Operator":{"nID":1,"oSubject":{"nID":1,"sID":"ПАО","sLabel":"ПАО ПриватБанк","sLabelShort":"ПриватБанк"},"sOKPO":"093205","sFormPrivacy":"ПАО","sName":"ПриватБанк","sNameFull":"Банк ПриватБанк"},"oData":"{}","sURL":"https://dniprorada.igov.org.ua","bHidden":false}],"sInfo":"","sFAQ":"","sLaw":"","nSub":0}
```

**HTTP Context: http://server:port/wf/service/action/item/setService** - Изменение сервиса. Можно менять/добавлять, но не удалять данные внутри сервиса, на разной глубине вложенности. Передается json в теле POST запроса в том же формате, в котором он был в getService.  (описание занесено в Swagger)

**HTTP Metod: POST**

Вовращает: HTTP STATUS 200 + json представление сервиса после изменения. Чаще всего то же, что было передано в теле POST запроса + сгенерированные id-шники вложенных сущностей, если такие были.

Пример:
https://test.igov.org.ua/wf/service/action/item/setService
```json
{
    "sSubjectOperatorName": "МВС",
    "subjectOperatorName": "МВС",
    "nID": 1,
    "sName": "Отримати довідку про несудимість",
    "nOrder": 1,
    "aServiceData": [
        {
            "nID": 1,
            "nID_City": {
                "nID": 2,
                "sName": "Кривий Ріг",
                "nID_Region": {
                    "nID": 1,
                    "sName": "Дніпропетровська"
                }
            },
            "nID_ServiceType": {
                "nID": 1,
                "sName": "Внешняя",
                "sNote": "Пользователь переходит по ссылке на услугу, реализованную на сторонней платформе"
            },
            "oSubject_Operator": {
                "nID": 1,
                "oSubject": {
                    "nID": 1,
                    "sID": "ПАО",
                    "sLabel": "ПАО ПриватБанк",
                    "sLabelShort": "ПриватБанк"
                },
                "sOKPO": "093205",
                "sFormPrivacy": "ПАО",
                "sName": "ПриватБанк",
                "sNameFull": "Банк ПриватБанк"
            },
            "oData": "{}",
            "sURL": "https://dniprorada.igov.org.ua",
            "bHidden": false
        }
    ],
    "sInfo": "",
    "sFAQ": "",
    "sLaw": "",
    "nSub": 0
}
```
Ответ:
```json
{
    "sSubjectOperatorName": "МВС",
    "subjectOperatorName": "МВС",
    "nID": 1,
    "sName": "Отримати довідку про несудимість",
    "nOrder": 1,
    "aServiceData": [
        {
            "nID": 1,
            "nID_City": {
                "nID": 2,
                "sName": "Кривий Ріг",
                "nID_Region": {
                    "nID": 1,
                    "sName": "Дніпропетровська"
                }
            },
            "nID_ServiceType": {
                "nID": 1,
                "sName": "Внешняя",
                "sNote": "Пользователь переходит по ссылке на услугу, реализованную на сторонней платформе"
            },
            "oSubject_Operator": {
                "nID": 1,
                "oSubject": {
                    "nID": 1,
                    "sID": "ПАО",
                    "sLabel": "ПАО ПриватБанк",
                    "sLabelShort": "ПриватБанк"
                },
                "sOKPO": "093205",
                "sFormPrivacy": "ПАО",
                "sName": "ПриватБанк",
                "sNameFull": "Банк ПриватБанк"
            },
            "oData": "{}",
            "sURL": "https://dniprorada.igov.org.ua",
            "bHidden": false
        }
    ],
    "sInfo": "",
    "sFAQ": "",
    "sLaw": "",
    "nSub": 0
}
```
**HTTP Context: http://server:port/wf/service/action/item/removeService** - Удаление сервиса. 
 (описание занесено в Swagger)

**HTTP Metod: DELETE**

* nID - ИД-номер сервиса
* bRecursive (не обязательно, по умолчанию false) - Удалять рекурсивно все данные связанные с сервисом. Если false, то при наличии вложенных сущностей, ссылающихся на эту, сервис удален не будет.
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Вовращает:

HTTP STATUS 200 - удаление успешно.
HTTP STATUS 304 - не удалено.

Пример 1:
https://test.igov.org.ua/wf/service/action/item/removeService?nID=1

Ответ 1: HTTP STATUS 304

Пример 2:
https://test.igov.org.ua/wf/service/action/item/removeService?nID=1&bRecursive=true

Ответ 2: HTTP STATUS 200
```json
{
    "code": "success",
    "message": "class org.igov.activiti.common.Service id: 1 removed"
}
```

**HTTP Context: http://server:port/wf/service/action/item/removeServiceData** - Удаление сущности ServiceData.  (описание занесено в Swagger)

**HTTP Metod: DELETE**

* nID - идентификатор ServiceData
* bRecursive (не обязательно, по умолчанию false) - Удалять рекурсивно все данные связанные с ServiceData. Если false, то при наличии вложенных сущностей, ссылающихся на эту, ServiceData удалена не будет.
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Вовращает:

HTTP STATUS 200 - удаление успешно.
HTTP STATUS 304 - не удалено.

Пример:
https://test.igov.org.ua/wf/service/action/item/removeServiceData?nID=1&bRecursive=true

Ответ: HTTP STATUS 200
```json
{
    "code": "success",
    "message": "class org.igov.activiti.common.ServiceData id: 1 removed"
}
```

**HTTP Context: http://server:port/wf/service/action/item/removeSubcategory** - Удаление подкатегории.  (описание занесено в Swagger)

**HTTP Metod: DELETE**

* nID - идентификатор подкатегории.
* bRecursive (не обязательно, по умолчанию false) - Удалять рекурсивно все данные связанные с подкатегорией. Если false, то при наличии вложенных сущностей, ссылающихся на эту, подкатегория удалена не будет.
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Вовращает:

HTTP STATUS 200 - удаление успешно.
HTTP STATUS 304 - не удалено.

Пример 1:
https://test.igov.org.ua/wf/service/action/item/removeSubcategory?nID=1

Ответ 1: HTTP STATUS 304

Пример 2:
https://test.igov.org.ua/wf/service/action/item/removeSubcategory?nID=1&bRecursive=true

Ответ 2: HTTP STATUS 200
```json
{
    "code": "success",
    "message": "class org.igov.activiti.common.Subcategory id: 1 removed"
}
```

**HTTP Context: http://server:port/wf/service/action/item/removeCategory** - Удаление категории.  (описание занесено в Swagger)

**HTTP Metod: DELETE**

* nID - идентификатор подкатегории.
* bRecursive (не обязательно, по умолчанию false) - Удалять рекурсивно все данные связанные с категорией. Если false, то при наличии вложенных сущностей, ссылающихся на эту, категория удалена не будет.
* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Вовращает:

HTTP STATUS 200 - удаление успешно.
HTTP STATUS 304 - не удалено.

Пример 1:
https://test.igov.org.ua/wf/service/action/item/removeCategory?nID=1

Ответ 1: HTTP STATUS 304

Пример 2:
https://test.igov.org.ua/wf/service/action/item/removeCategory?nID=1&bRecursive=true

Ответ 2: HTTP STATUS 200
```json
{
    "code": "success",
    "message": "class org.igov.activiti.common.Category id: 1 removed"
}
```

**HTTP Context: http://server:port/wf/service/action/item/removeServicesTree** - Удаление всего дерева сервисов и категорий.  (описание занесено в Swagger)

**HTTP Metod: DELETE**

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Вовращает:

HTTP STATUS 200 - удаление успешно.

Пример 1:
https://test.igov.org.ua/wf/service/action/item/removeServicesTree

Ответ 1: HTTP STATUS 200
```json
{
    "code": "success",
    "message": "ServicesTree removed"
}
```

**HTTP Context: http://server:port/wf/service/object/place/getPlaces** - Получения дерева мест (регионов и городов). getPlaces

**HTTP Metod: GET**

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Пример:
https://test.igov.org.ua/wf/service/object/place/getPlaces

Ответ:
```json
[
    {
        "nID": 1,
        "sName": "Дніпропетровська",
        "aCity": [
            {
                "nID": 1,
                "sName": "Дніпропетровськ"
            },
            {
                "nID": 2,
                "sName": "Кривий Ріг"
            }
        ]
    },
    {
        "nID": 2,
        "sName": "Львівська",
        "aCity": [
            {
                "nID": 3,
                "sName": "Львів"
            }
        ]
    },
    {
        "nID": 3,
        "sName": "Івано-Франківська",
        "aCity": [
            {
                "nID": 4,
                "sName": "Івано-Франківськ"
            },
            {
                "nID": 5,
                "sName": "Калуш"
            }
        ]
    },
    {
        "nID": 4,
        "sName": "Миколаївська",
        "aCity": []
    },
    {
        "nID": 5,
        "sName": "Київська",
        "aCity": [
            {
                "nID": 6,
                "sName": "Київ"
            }
        ]
    },
    {
        "nID": 6,
        "sName": "Херсонська",
        "aCity": [
            {
                "nID": 7,
                "sName": "Херсон"
            }
        ]
    },
    {
        "nID": 7,
        "sName": "Рівненська",
        "aCity": [
            {
                "nID": 8,
                "sName": "Кузнецовськ"
            }
        ]
    },
    {
        "nID": 8,
        "sName": "Волинська",
        "aCity": [
            {
                "nID": 9,
                "sName": "Луцьк"
            }
        ]
    }
]
```

**HTTP Context: http://server:port/wf/service/object/place/setPlaces** - Изменение дерева мест (регионов и городов). Можно менять регионы (не добавлять и не удалять) + менять/добавлять города (но не удалять), Передается json в теле POST запроса в том же формате, в котором он был в getPlaces.  (описание занесено в Swagger)

**HTTP Metod: POST**

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Возвращает: HTTP STATUS 200 + json представление сервиса после изменения. Чаще всего то же, что было передано в теле POST запроса + сгенерированные id-шники вложенных сущностей, если такие были.

Пример:
https://test.igov.org.ua/wf/service/object/place/setPlaces
```json
[
    {
        "nID": 1,
        "sName": "Дніпропетровська",
        "aCity": [
            {
                "nID": 1,
                "sName": "Cічеслав"
            },
            {
                "nID": 2,
                "sName": "Кривий Ріг"
            }
        ]
    }
]
```
Ответ: HTTP STATUS 200
```json
[
    {
        "nID": 1,
        "sName": "Дніпропетровська",
        "aCity": [
            {
                "nID": 1,
                "sName": "Cічеслав"
            },
            {
                "nID": 2,
                "sName": "Кривий Ріг"
            }
        ]
    }
]
```

**HTTP Context: http://server:port/wf/service/action/item/setServicesTree** - Изменение дерева категорий (с вложенными подкатегориями и сервисами). Можно менять категории (не добавлять и не удалять) + менять/добавлять (но не удалять) вложенные сущности, Передается json в теле POST запроса в том же формате, в котором он был в getServicesTree.   (описание занесено в Swagger)

**HTTP Metod: POST**

* nID_Subject - ID авторизированого субъекта (добавляется в запрос автоматически после аутентификации пользователя)

Возвращает: HTTP STATUS 200 + json представление сервиса после изменения. Чаще всего то же, что было передано в теле POST запроса + сгенерированные id-шники вложенных сущностей, если такие были.

Пример:
https://test.igov.org.ua/wf/service/action/item/setServicesTree
```json
[
    {
        "nID": 1,
        "sID": "Citizen",
        "sName": "Гражданин",
        "nOrder": 1,
        "aSubcategory": [
            {
                "nID": 5,
                "sName": "Праця2",
                "sID": "Work",
                "nOrder": 3,
                "aService": [
                    {
                        "nID": 3,
                        "sName": "Видача картки обліку об’єкта торговельного призначення, сфери послуг та з виробництва продуктів харчування",
                        "nOrder": 3
                    },
                    {
                        "nID": 4,
                        "sName": "Легалізація об’єднань громадян шляхом повідомлення",
                        "nOrder": 4
                    }
                ]
            }
            ]
         }
]
```
Ответ: HTTP STATUS 200
```json
[
    {
        "nID": 1,
        "sID": "Citizen",
        "sName": "Гражданин",
        "nOrder": 1,
        "aSubcategory": [
            {
                "nID": 5,
                "sName": "Праця2",
                "sID": "Work",
                "nOrder": 3,
                "aService": [
                    {
                        "nID": 3,
                        "sName": "Видача картки обліку об’єкта торговельного призначення, сфери послуг та з виробництва продуктів харчування",
                        "nOrder": 3
                    },
                    {
                        "nID": 4,
                        "sName": "Легалізація об’єднань громадян шляхом повідомлення",
                        "nOrder": 4
                    }
                ]
            }
            ]
         }
]
```

Для добавления новой подкатегории нужно передать запрос вида:
```
[
  {
    "nID": 1,
    "aSubcategory": [
      {
        "sID": "Yd",
        "sName": "Yjdd",
        "nOrder": "1",
        "oCategory": {
          "nID": 1
        }
      }
    ]
  }
]
```
Обязательно нужно указывать внутри подкатегории ссылку на категорию, с помощью
```
"oCategory": {
  "nID": 1
}
```

Для добавления нового сервиса нужно передать запрос вида:
```
[
  {
    "nID": 1,
    "aSubcategory": [
      {
        "nID": 3,
        "aService": [
          {
            "sName": "service name",
            "nOrder": 10,
            "sSubjectOperatorName": "subjectOperatorName",
            "oSubcategory": {
              "nID": 3
            },
            "sInfo": "",
            "sFAQ": "",
            "sLaw": ""
          }
        ]
      }
    ]
  }
]
```
Обязательно нужно указывать внутри сервиса ссылку на подкатегорию, с помощью
```
"oSubcategory": {
  "nID": 3
}
```
А также обязательные поля "sInfo", "sFAQ", "sLaw" - можно с пустыми значениями.


<a name="16_getWorkflowStatistics">
#### 16. Получение статистики по задачам в рамках бизнес процесса  (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/action/task/download_bp_timing?sID_BP_Name=XXX&sDateAt=XXX8&sDateTo=XXX**

* sID_BP_Name - ID бизнес процесса
* sDateAt - Дата начала периода для выборки в формате yyyy-MM-dd
* sDateTo - Дата окончания периода для выборки в формате yyyy-MM-dd
* nRowsMax - необязательный параметр. Максимальное значение завершенных задач для возврата. По умолчанию 1000.
* nRowStart - Необязательный параметр. Порядковый номер завершенной задачи в списке для возврата. По умолчанию 0.
* bDetail - Необязательный параметр. Необходим ли расширенный вариант (с полями задач). По умолчанию true.
* saFields - настраиваемые поля (название поля -- формула, issue 907)
* saFieldSummary - сведение полей, которое производится над выборкой (issue 916)

Метод возвращает .csv файл со информацией о завершенных задачах в указанном бизнес процессе за период.
Если указан параметр saFieldSummary -- то также будет выполнено "сведение" полей (описано ниже). Если не указан, то формат выходного файла:<br/>
 * nID_Process -  ид задачи<br/>       
 * sLoginAssignee - кто выполнял задачу<br/>
 * sDateTimeStart - Дата и время начала<br/>
 * nDurationMS - Длительность выполнения задачи в миллисекундах<br/>
 * nDurationHour - Длительность выполнения задачи в часах<br/>
 * sName - Название задачи<br/>
 * Поля из FormProperty (если bDetail=true)<br/>
 * настраиваемые поля из saFields <br/>


Пример:
https://test.region.igov.org.ua/wf/service/action/task/download_bp_timing?sID_BP_Name=lviv_mvk-1&sDateAt=2015-06-28&sDateTo=2015-07-01

Пример выходного файла

```
"Assignee","Start Time","Duration in millis","Duration in hours","Name of Task"
"kermit","2015-06-21:09-20-40","711231882","197","Підготовка відповіді на запит: пошук документа"
```
**Сведение полей**<br/>
параметр saFieldSummary может содержать примерно такое значение: "sRegion;nSum=sum(nMinutes);nVisites=count()"<br/>
тот элемент, который задан первым в параметре saFieldSummary - является "ключевым полем"<br/>
следующие элементы состоят из названия для колонки, агрегирующей функции и названия агрегируемого поля. Например: "nSum=sum(nMinutes)"<br/>
где:
* nSum - название поля, куда будет попадать результат
* sum - "оператор сведения"
* nMinutes - "расчетное поле", переменная, которая хранит в себе значение уже существующего или посчитанного поля формируемой таблицы

перечень поддерживаемых "операторов сведения":
 * count() - число строк/элементов (не содержит аргументов)
 * sum(field) - сумма чисел (содержит аргумент - название обрабатываемого поля)
 * avg(field) - среднее число (содержит аргумент - название обрабатываемого поля)
 
 Операторы можно указывать в произвольном регистре, т.е. SUM, sum и SuM "распознаются" как оператор суммы sum. <br/>
 Для среднего числа также предусмотрено альтернативное название "average".<br/>
 Если в скобках не указано поле, то берется ключевое.<br/>
 
Значение "ключевого поля" переносится в новую таблицу без изменений в виде единой строки,и все остальные сводные поля подсчитываются 
исключительно в контексте значения этого ключевого поля, и проставляютя соседними полями в рамках этой единой строки.

Особенности подсчета:
* если нету исходных данных или нету такого ключевого поля, то ничего не считается (в исходном файле просто будут заголовки)
* если расчетного поля нету, то поле не считается (т.е. сумма и количество для ключевого не меняется)
* тип поля Сумма и Среднее -- дробное число, Количество -- целое. Исходя из этого при подсчете суммы значение конвертируется в число, если конвертация неудачна, то сумма не меняется. 
(т.е. если расчетное поле чисто текстовое, то сумма и среднее будет 0.0) 

Пример:
https://test.region.igov.org.ua/wf/service/action/task/download_bp_timing?sID_BP_Name=_test_queue_cancel&sDateAt=2015-04-01&sDateTo=2015-10-31&saFieldSummary=email;nSum=sum(nDurationHour);nVisites=count();nAvg=avg(nDurationHour)

Ответ:
```
"email","nSum","nVisites","nAvg"
"email1","362.0","5","72.4"
"email2","0.0","1","0.0"
```

**Настраиваемые поля**<br/>
Параметр saFields может содержать набор полей с выражениями, разделенными символом ; <br/>
Вычисленное выражение, расчитанное на основании значений текущей задачи, подставляется в выходной файл <br/>

Пример выражения <br/> saFields="nCount=(sID_UserTask=='usertask1'?1:0);nTest=(sAssignedLogin=='kermit'?1:0)" <br/> 
где:
* nCount, nTest - названия колонок в выходном файле
* sID_UserTask, sAssignedLogin - ID таски в бизнес процессе и пользователь, на которого заассайнена таска, соответственно

Пример:
https://test.region.igov.org.ua/wf/service/action/task/download_bp_timing?sID_BP_Name=_test_queue_cancel&sDateAt=2015-04-01&sDateTo=2015-10-31&saFields="nCount=(sID_UserTask=='usertask1'?1:0);nTest=(sAssignedLogin=='kermit'?1:0)"

Результат:
```
"nID_Process","sLoginAssignee","sDateTimeStart","nDurationMS","nDurationHour","sName","bankIdPassport","bankIdfirstName","bankIdlastName","bankIdmiddleName","biometrical","date_of_visit","date_of_visit1","email","finish","have_passport","initiator","phone","urgent","visitDate","nCount","nTest"
"5207501","kermit","2015-09-25:12-18-28","1433990","0","обробка дмс","АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЇНИ В ДНІПРОПЕТРОВСЬКІЙ ОБЛАСТІ 18.03.2002","ДМИТРО","ДУБІЛЕТ","ОЛЕКСАНДРОВИЧ","attr1_no","2015-10-09 09:00:00.00","dd.MM.yyyy HH:MI","nazarenkod1990@gmail.com","attr1_ok","attr1_yes","","38","attr1_no","{""nID_FlowSlotTicket"":27764,""sDate"":""2015-10-09 09:00:00.00""}","0.0","1.0"
"5215001","kermit","2015-09-25:13-03-29","75259","0","обробка дмс","АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЇНИ В ДНІПРОПЕТРОВСЬКІЙ ОБЛАСТІ 18.03.2002","ДМИТРО","ДУБІЛЕТ","ОЛЕКСАНДРОВИЧ","attr1_no","2015-10-14 11:15:00.00","dd.MM.yyyy HH:MI","nazarenkod1990@gmail.com","attr1_ok","attr1_yes","","38","attr1_no","{""nID_FlowSlotTicket"":27767,""sDate"":""2015-10-14 11:15:00.00""}","0.0","1.0"
"5215055","dn200986zda","2015-09-25:13-05-22","1565056","0","обробка дмс","АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЇНИ В ДНІПРОПЕТРОВСЬКІЙ ОБЛАСТІ 18.03.2002","ДМИТРО","ДУБІЛЕТ","ОЛЕКСАНДРОВИЧ","attr1_no","2015-09-28 08:15:00.00","dd.MM.yyyy HH:MI","dmitrij.zabrudskij@privatbank.ua","attr2_missed","attr1_yes","","38","attr1_no","{""nID_FlowSlotTicket"":27768,""sDate"":""2015-09-28 08:15:00.00""}","0.0","0.0"
```

<a name="17_workWithHistoryEvent_Services">
#### 17. Работа с обьектами событий по услугам  (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>
**HTTP Metod: GET**

**HTTP Context: https://server:port/wf/service/action/event/getHistoryEvent_Service?nID_Protected=ххх***
получает объект события по услуге, по одной из следующий комбинаций параметров:
 - только sID_Order, строка-ид события по услуге, формат XXX-XXXXXX, где первая часть -- ид сервера, где расположена задача, 
 вторая часть -- nID_Protected, т.е. ид задачи + контрольная сумма по алгоритму Луна (описано ниже)
 - только nID_Protected -- "старая" нумерация, ид сервера в таком случае равно 0
 - nID_Server + nID_Protected (если сервера нету, то он 0)
 - nID_Server + nID_Process (если сервера нету, то он 0)
  
параметры запроса: 
* sID_Order -- строка-ид события по услуге, в формате XXX-XXXXXX = nID_Server-nID_Protected (опционально, если есть другие параметры)
* nID_Protected -- зашифрованое ид задачи, nID задачи + контрольная цифра по алгоритму Луна (опционально, если задан sID_Order)
* nID_Process -- ид задачи (опционально, если задан один из предыдущих параметров)
* nID_Server -- ид сервера, где расположена задача (опционально, по умолчанию 0)

для sID_Order проверяется соответсвие формату (должен содержать "-"), если черточки нету -- то перед строкой добавляется "0-" 
 
для nID_Protected проверяется его корректность , где последняя цифра - это последний разряд контрольной суммы (по
<a href="https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9B%D1%83%D0%BD%D0%B0">алгоритму Луна</a>) для всего числа без нее.
- если не совпадает -- возвращается ошибка "CRC Error" (код состояния HTTP 403) 
- если совпадает --  ищется запись по nID_Process = nID_Protected без последней цифры (берется последняя по дате добавления)
- Если не найдена запись, то возвращает объект ошибки со значением "Record not found"  (код состояния HTTP 403)
- иначе возвращает обьект

пример:
http://test.igov.org.ua/wf/service/action/event/getHistoryEvent_Service?nID_Protected=11

**HTTP Metod: GET**

 (описание занесено в Swagger) 

**HTTP Context: https://server:port/wf/service/action/event/addHistoryEvent_Service?nID_Task=xxx&sStatus=xxx&nID_Subject=xxx***

 добавляет объект события по услуге, параметры: 
 * nID_Process - ИД-номер задачи (long)
 * nID_Server - ид сервера, где расположена задача (опционально, по умолчанию 0)
 * nID_Subject - ИД-номер (long) 
 * sID_Status - строка-статус 
 * sProcessInstanceName - название услуги (для Журнала событий)
 * nID_Service -- ид услуги (long, опционально)
 * nID_Region -- ид области (long, опционально)
 * sID_UA -- ид страны (строка, опционально)
 * soData - строка-объект с данными (опционально, для поддержки дополнения заявки со стороны гражданина)
 * sToken - строка-токена (опционально, для поддержки дополнения заявки со стороны гражданина)
 * sHead - строка заглавия сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
 * sBody - строка тела сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)

при добавлении сначала проверяется, не было ли уже такой записи для данного nID_Process и nID_Server. 
если было -- ошибка ```Cannot create event_service with the same nID_Process and nID_Server!```

потом генерируется поле nID_Protected по принципу: nID_Protected = nID_Process (ид задачи) + "контрольная цифра" 

*контрольная цифра* -- это последний разряд суммы цифр числа по
<a href="https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9B%D1%83%D0%BD%D0%B0">алгоритму Луна</a>
это поле используется для проверки корректности запрашиваемого ид записи (в методах get и update)

также генерируется поле sID_Order по принципу: sID_Order = nID_Server + "-" + nID_Protected

пример:
http://test.igov.org.ua/wf/service/action/event/addHistoryEvent_Service?nID_Process=2&sID_Status=new&nID_Subject=2&sProcessInstanceName=test_bp

ответ:
```json
{
	"sID":null,
	"nID_Task":2,
	"nID_Subject":2,
	"sStatus":"new",
	"sID_Status":"new",
	"sDate":"2015-11-09 18:50:02.772",
	"nID_Service":null,
	"nID_Region":null,
	"sID_UA":null,
	"nRate":0,
	"soData":"[]",
	"sToken":null,
	"sHead":null,
	"sBody":null,
	"nTimeMinutes":null,
	"sID_Order":"0-22",
	"nID_Server":0,
	"nID_Protected":22,
	"nID":40648
}
```

**HTTP Metod: GET**

(описание занесено в Swagger) 

**HTTP Context: https://server:port/wf/service/action/event/updateHistoryEvent_Service?nID=xxx&sStatus=xxx*** 

 обновляет объект события по услуге, параметры: 
 
 * sID_Order -- строка-ид события по услуге, в формате XXX-XXXXXX = nID_Server-nID_Protected(опционально, если задан sID_Order или nID_Process с/без nID_Server)
 * nID_Protected -- зашифрованое ид задачи, nID задачи + контрольная цифра по алгоритму Луна (опционально, если задан sID_Order или nID_Process с/без nID_Server)
 * nID_Process - ид задачи (опционально, если задан sID_Order или nID_Protected с/без nID_Server)
 * nID_Server -- ид сервера, где расположена задача (опционально, по умолчанию 0) 
 * nID_Process - ИД-номер задачи (long)
 * sID_Status - строка-статус
 * soData - строка-объект с данными (опционально, для поддержки дополнения заявки со стороны гражданина)
 * sToken - строка-токена (опционально, для поддержки дополнения заявки со стороны гражданина)
 * sHead - строка заглавия сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
 * sBody - строка тела сообщения (опционально, для поддержки дополнения заявки со стороны гражданина)
 * nTimeMinutes - время обработки задачи (в минутах, опционально)

пример
http://test.igov.org.ua/wf/service/action/event/updateHistoryEvent_Service?nID_Process=1&sID_Status=finish
Также при апдейте охраняется информация о действии в <a href="https://github.com/e-government-ua/i/blob/test/docs/specification.md#13_workWithHistoryEvents">Моем Журнале</a>
1) запись "Ваша заявка №\[nID_Process\] змiнила свiй статус на \[sID_Status\]"
2) если есть параметр soData, то еще создается запись в виде:
 - "По заявці №\[nID_Process\] задане прохання уточнення: \[sBody\]" (если sToken не пустой) -- согласно сервису в <a href="https://github.com/e-government-ua/i/blob/test/docs/specification.md#38_setTaskQuestions">запроса на уточнение</a>
 - "По заявці №\[nID_Process\] дана відповідь громадянином: \[sBody\]" (если sToken пустой) -- согласно сервису <a href="https://github.com/e-government-ua/i/blob/test/docs/specification.md#39_setTaskAnswer">ответа на запрос по уточнению</a>
 - плюс перечисление полей из soData в формате таблицы Поле / Тип / Текущее значение
 
<a name="18_workWithFlowSlot">
#### 18. Электронные очереди (слоты потока, расписания и тикеты) (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Context: http://server:port/wf/service/action/flow/getFlowSlots_ServiceData** - Получение слотов по сервису сгруппированных по дням. (описание занесено в Swagger)

**HTTP Metod: GET**

Параметры:
* nID_Service -номер-ИД услуги  (обязательный если нет sID_BP и nID_ServiceData)
* nID_ServiceData - ID сущности ServiceData (обязательный если нет sID_BP и nID_Service)
* sID_BP - строка-ИД бизнес-процесса (обязательный если нет nID_ServiceData и nID_Service)
* nID_SubjectOrganDepartment - ID департамента субьекта-органа  (опциональный, по умолчанию false)
* bAll - если false то из возвращаемого объекта исключаются элементы, содержащие "bHasFree":false "bFree":false (опциональный, по умолчанию false)
* nDays - колличество дней от сегодняшего включительно(или sDateStart, если задан), до nDays в будующее за который нужно вернуть слоты (опциональный, по умолчанию 177 - пол года)
* nFreeDays - дни со слотами будут включаться в результат пока не наберется указанное кол-во свободных дней (опциональный, по умолчанию 60)
* sDateStart - опциональный параметр, определяющие дату начала в формате "yyyy-MM-dd", с которую выбрать слоты. При наличии этого параметра слоты возвращаются только за указанный период(число дней задается nDays).

Пример:
https://test.igov.org.ua/wf/service/action/flow/getFlowSlots_ServiceData?nID_ServiceData=1
или
https://test.region.igov.org.ua/wf/service/action/flow/getSheduleFlowIncludes?sID_BP=kiev_mreo_1

Ответ:  HTTP STATUS 200
```json
{
    "aDay": [
        {
            "sDate": "2015-07-19",
            "bHasFree": true,
            "aSlot": [
                {
                    "nID": 1,
                    "sTime": "18:00",
                    "nMinutes": 15,
                    "bFree": true
                }
            ]
        },
        {
            "sDate": "2015-07-20",
            "bHasFree": true,
            "aSlot": [
                {
                    "nID": 3,
                    "sTime": "18:15",
                    "nMinutes": 15,
                    "bFree": true
                }
            ]
        }
    ]
}
```
Калькулируемые поля в ответе:

флаг "bFree" - является ли слот свободным? Слот считается свободным если на него нету тикетов у которых nID_Task_Activiti равен null, а у тех у которых nID_Task_Activiti = null - время создания тикета (sDateEdit) не позднее чем текущее время минус 5 минут (предопределенная константа)

флаг "bHasFree" равен true , если данных день содержит хотя бы один свободный слот.


**HTTP Context: http://server:port/wf/service/flow/setFlowSlots_ServiceData** - Создание или обновление тикета в указанном слоте. (описание занесено в Swagger)

**HTTP Metod: POST**

Параметры:
* nID_FlowSlot - ID сущности FlowSlot (обязательный)
* nID_Subject - ID сущнсоти Subject - субьект пользователь услуги, который подписывается на слот (обязательный)
* nID_Task_Activiti - ID таски активити процесса предоставления услуги (не обязательный - вначале он null, а потом засчивается после подтверждения тикета, и создания процесса)

Пример:
http://test.igov.org.ua/wf/service/action/flow/setFlowSlot_ServiceData
* nID_FlowSlot=1
* nID_Subject=2

Ответ:  HTTP STATUS 200

{
    "nID_Ticket": 1000
}

Поля в ответе:

поле "nID_Ticket" - ID созданной/измененной сущности FlowSlotTicket.


**HTTP Context: http://server:port/wf/service/action/flow/buildFlowSlots** - Генерация слотов на заданный интервал для заданного потока. (описание занесено в Swagger)

**HTTP Metod: POST**

Параметры:
* nID_Flow_ServiceData - номер-ИД потока (обязательный если нет sID_BP)
* sID_BP - строка-ИД бизнес-процесса потока (обязательный если нет nID_Flow_ServiceData)
* sDateStart - дата "начиная с такого-то момента времени", в формате "2015-06-28 12:12:56.001" (опциональный)
* sDateStop - дата "заканчивая к такому-то моменту времени", в формате "2015-07-28 12:12:56.001" (опциональный)

Пример:
http://test.igov.org.ua/wf/service/action/flow/buildFlowSlots
* nID_Flow_ServiceData=1
* sDateStart=2015-06-01 00:00:00.000
* sDateStop=2015-06-07 00:00:00.000

Ответ:  HTTP STATUS 200 + json перечисление всех сгенерированных слотов.

Ниже приведена часть json ответа:
```json
[
    {
        "nID": 1000,
        "sTime": "08:00",
        "nMinutes": 15,
        "bFree": true
    },
    {
        "nID": 1001,
        "sTime": "08:15",
        "nMinutes": 15,
        "bFree": true
    },
    {
        "nID": 1002,
        "sTime": "08:30",
        "nMinutes": 15,
        "bFree": true
    },
...
]
```
Если на указанные даты слоты уже сгенерены то они не будут генерится повторно, и в ответ включаться не будут.


**HTTP Context: http://server:port/wf/service/action/flow/clearFlowSlots** - Удаление слотов на заданный интервал для заданного потока. (описание занесено в Swagger)


**HTTP Metod: DELETE**

Параметры:
* nID_Flow_ServiceData - номер-ИД потока (обязательный если нет sID_BP)
* sID_BP - строка-ИД бизнес-процесса потока (обязательный если нет nID_Flow_ServiceData)
* sDateStart - дата "начиная с такого-то момента времени", в формате "2015-06-28 12:12:56.001" (обязательный)
* sDateStop - дата "заканчивая к такому-то моменту времени", в формате "2015-07-28 12:12:56.001" (обязательный)
* bWithTickets - удалять ли слоты с тикетами, отвязывая тикеты от слотов? (опциональный, по умолчанию false)

Пример:
http://test.igov.org.ua/wf/service/action/flow/clearFlowSlots?nID_Flow_ServiceData=1&sDateStart=2015-06-01 00:00:00.000&sDateStop=2015-06-07 00:00:00.000

Ответ:  HTTP STATUS 200 + json Обьект содержащий 2 списка:
* aDeletedSlot - удаленные слоты
* aSlotWithTickets - слоты с тикетами. Елси bWithTickets=true то эти слоты тоже удаляются и будут перечислены в aDeletedSlot, иначе - не удаляются.

Ниже приведена часть json ответа:
```json
{
    "aDeletedSlot": [
        {
            "nID": 1000,
            "sTime": "08:00",
            "nMinutes": 15,
            "bFree": true
        },
        {
            "nID": 1001,
            "sTime": "08:15",
            "nMinutes": 15,
            "bFree": true
        },
        ...
     ],
     "aSlotWithTickets": []
}
```

<a name="19">
#### 19. Работа с джоинами субьектами (отделениями/филиалами) (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>
(таска: https://github.com/e-government-ua/i/issues/487)

Поля:
* nID - ИД-номер автоитеррируемый (уникальный, обязательный) (long)
* nID_SubjectOrgan - ИД-номер (long)
* sNameRu - строка <200 символов
* sNameUa - ИД-строка <200 символов
* sID_Privat - ИД-строка ключ-частный <60 символов //опциональный
* sID_Public - строка ключ-публичный <60 символов
* sGeoLongitude - строка долготы //опциональный
* sGeoLatitude - строка широты //опциональный
* nID_Region - ИД-номер //опциональный
* nID_City - ИД-номер //опциональный
* sID_UA - ИД-строка кода классификатора КОАТУУ //опциональный


**getSubjectOrganJoins - получает весь массив объектов п.2 (либо всех либо в рамках заданных в запросе nID_Region, nID_City или sID_UA)**
<br>
**Method: GET**
Параметры:
* nID_SubjectOrgan - ИД-номер (в урл-е)
* nID_Region - ИД-номер (в урл-е) //опциональный (только если надо задать или задан)
* nID_City - ИД-номер (в урл-е) //опциональный (только если надо задать или задан)
* sID_UA - ИД-строка (в урл-е) //опциональный (только если надо задать или задан)

Пример ответа:
```json
[
	{	"nID_SubjectOrgan":32343
		,"sNameUa":"Українська мова"
		,"sNameRu":"Русский язык"
		,"sID_Privat":"12345"
		,"sID_Public":"130501"
		,"sGeoLongitude":"15.232312"
		,"sGeoLatitude":"23.234231"
		,"nID_Region":11
		,"nID_City":33
		,"sID_UA":"1"
	}
]
```
Пример:
https://test.igov.org.ua/wf/service/subject/getSubjectOrganJoins?nID_SubjectOrgan=1&sID_UA=1


**setSubjectOrganJoin - добавляет/обновляет массив объектов п.2 (сопоставляя по по ИД, и связывая новые с nID_Region, nID_City или sID_UA, по совпадению их названий)**
<br>
**Method: POST**
Параметры:
* nID_SubjectOrgan - ИД-номер
* nID //опциональный, если добавление
* sNameRu //опциональный
* sNameUa //опциональный
* sID_Privat //опциональный
* sID_Public //опциональный, если апдейт
* sGeoLongitude //опциональный
* sGeoLatitude //опциональный
* nID_Region //опциональный
* nID_City //опциональный
* sID_UA //опциональный

Пример:
https://test.igov.org.ua/wf/service/subject/setSubjectOrganJoin?nID_SubjectOrgan=1&sNameRu=Днепр.РОВД
<br>


**removeSubjectOrganJoins - удаляет массив объектов п.2 (находя их по ИД)** (описание занесено в Swagger)
<br>
**Method: POST**
Параметры:
* nID_SubjectOrgan - ИД-номер (в урл-е)
* asID_Public - массив ИД-номеров  (в урл-е) (например [3423,52354,62356,63434])

Пример:
https://test.igov.org.ua/wf/service/subject/removeSubjectOrganJoins?nID_SubjectOrgan=1&asID_Public=130505,130506,130507,130508


<a name="20">
#### 20. Получение кнопки для оплаты через LiqPay (описание занесено в Swagger)
<br><a href="#0_contents">↑Up</a>
**Method: GET**

**HTTP Context: https://server:port/wf/service/finance/getPayButtonHTML_LiqPay**

Параметры:
* sID_Merchant - ид меранта
* sSum - сумма оплаты
* oID_Currency - валюта
* oLanguage - язык
* sDescription - описание
* sID_Order - ид заказа
* sURL_CallbackStatusNew - URL для отправки статуса
* sURL_CallbackPaySuccess - URL для отправки ответа
* nID_Subject - ид субъекта
* bTest - тестовый вызов или нет

Пример:
https://test.igov.org.ua/wf/service/finance/getPayButtonHTML_LiqPay?sID_Merchant=i10172968078&sSum=55,00&oID_Currency=UAH&oLanguage=RUSSIAN&sDescription=test&sID_Order=12345&sURL_CallbackStatusNew=&sURL_CallbackPaySuccess=&nID_Subject=1&bTest=true

<a name="21">
####21. Работа со странами  (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a>

----------------------

**HTTP Context: https://server:port/wf/service/finance/setCountry**

**Method: GET**

 апдейтит элемент (если задан один из уникальных ключей) или вставляет (если не задан nID), и отдает экземпляр нового объекта.
 
Параметры:
 * nID - ИД-номер, идентификатор записи
 * nID_UA - ИД-номер Код, в Украинском классификаторе (уникальное)
 * sID_Two - ИД-строка Код-двухсимвольный, международный (уникальное, строка 2 символа)
 * sID_Three - ИД-строка Код-трехсимвольный, международный (уникальное, строка 3 символа)
 * sNameShort_UA - Назва країни, коротка, Украинская (уникальное, строка до 100 символов)
 * sNameShort_EN - Назва країни, коротка, англійською мовою (уникальное, строка до 100 символов)
 * sReference_LocalISO - Ссылка на локальный ISO-стандарт, с названием (a-teg с href) (строка до 100 символов)
 
Если нет ни одного параметра  возвращает ошибку ```403. All args are null!```
Если есть один из уникальных ключей, но запись не найдена -- ошибка ```403. Record not found!```
Если кидать "новую" запись с одним из уже существующих ключей nID_UA -- то обновится существующая запись по ключу nID_UA, если будет дублироваться другой ключ -- ошибка ```403. Could not execute statement``` (из-за уникальных констрейнтов)

----------------------

**HTTP Context: https://server:port/wf/service/object/place/getCountries**

**Method: GET**

 возвращает весь список стран (залит справочник согласно <a href="https://uk.wikipedia.org/wiki/ISO_3166-1">Википеции</a> и <a href="http://www.profiwins.com.ua/uk/letters-and-orders/gks/4405-426.html">Класифікації країн світу</a>) 

пример: https://test.igov.org.ua/wf/service/object/place/getCountries

----------------------

**HTTP Context: https://server:port/wf/service/object/place/getCountry**

**Method: GET**

 возвращает объект Страны по первому из 4х ключей (nID, nID_UA, sID_Two, sID_Three). 

Если нет ни одного параметра  возвращает ошибку ```403. required at least one of parameters (nID, nID_UA, sID_Two, sID_Three)!```

Eсли задано два ключа от разных записей -- вернется та, ключ который "первее" в таком порядке: nID, nID_UA, sID_Two, sID_Three.

пример: https://test.igov.org.ua/wf/service/object/place/getCountry?nID_UA=123

ответ:
```json
{
"nID_UA":123,
"sID_Two":"AU",
"sID_Three":"AUS",
"sNameShort_UA":"Австралія",
"sNameShort_EN":"Australy",
"sReference_LocalISO":"ISO_3166-2:AU",
"nID":20340
}
```

----------------------

**HTTP Context: https://server:port/wf/service/finance/removeCountry**

**Method: GET**
 
 удаляет обьект по одному из четырех ключей (nID, nID_UA, sID_Two, sID_Three) или кидает ошибку ```403. Record not found!```.


<a name="22">
####22. Загрузка данных по задачам (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a>

**Method: GET**

**HTTP Context: https://server:port/wf/service/action/task/downloadTasksData**

Загрузка полей по задачам в виде файла.

Параметры:
* sID_BP - название бизнесс процесса
* sID_State_BP - состояние задачи, по умолчанию **исключается из фильтра** Берется из поля taskDefinitionKey задачи
* saFields - имена полей для выборкы разделенных через ';', чтобы добавить все поля можно использовать - '*' или не передевать параметр в запросе. Поле также может содержать названия колонок. Например, saFields=Passport\=${passport};{email}
* nASCI_Spliter - ASCII код для разделителя
* sFileName - имя исходящего файла, по умолчанию - **data_BP-bpName_<today>.txt"**
* sID_Codepage - кодировка исходящего файла, по умолчанию - **win1251**
* sDateCreateFormat - форматирование даты создания таски, по умолчанию - **yyyy-MM-dd HH:mm:ss**
* sDateAt - начальная дата создания таски, по умолчанию - **вчера**
* sDateTo - конечная дата создания таски, по умолчанию - **сегодня**
* nRowStart - начало выборки для пейджирования, по умолчанию - **0**
* nRowsMax - размер выборки для пейджирования, по умолчанию - **1000**
* bIncludeHistory - включить информацию по хисторик задачам, по умолчанию - **true**
* bHeader - добавить заголовок с названиями полей в выходной файл, по умолчанию - **false**
* saFieldsCalc - настраиваемые поля (название поля -- формула, issue 907)
* saFieldSummary - сведение полей, которое производится над выборкой (issue 916)

Поля по умолчанию, которые всегда включены в выборку:
* nID_Task - "id таски"
* sDateCreate - "дата создания таски" (в формате sDateCreateFormat)

Особенности обработки полей: 
* Если тип поля enum, то брать не его ИД пункта в энуме а именно значение Если тип поля enum, и в значении присутствует знак ";", то брать только то ту часть текста, которая находится справа от этого знака

Пример:
https://test.region.igov.org.ua/wf/service/action/task/downloadTasksData?&sID_BP=dnepr_spravka_o_doxodax&sID_State_BP=usertask1&sDateAt=2015-06-01&sDateTo=2015-08-01&saFields=${nID_Task};${sDateCreate};${area};;;0;${bankIdlastName} ${bankIdfirstName} ${bankIdmiddleName};4;${aim};${date_start};${date_stop};${place_living};${bankIdPassport};1;${phone};${email}&sID_Codepage=win1251&nASCI_Spliter=18&sDateCreateFormat=dd.mm.yyyy hh:MM:ss&sFileName=dohody.dat

Пример ответа:
```
1410042;16.32.2015 10:07:17;АНД (пров. Універсальний, 12);;;0;БІЛЯВЦЕВ ВОЛОДИМИР ВОЛОДИМИРОВИЧ;4;мета;16/07/2015;17/07/2015;мокешрмшгкеу;АЕ432204 БАБУШКИНСКИМ РО ДГУ УМВД 26.09.1996;1;380102030405;mendeleev.ua@gmail.com

995161;07.07.2015 05:07:27;;;;0;ДУБІЛЕТ ДМИТРО ОЛЕКСАНДРОВИЧ;4;для роботи;01/07/2015;07/07/2015;Дніпропетровська, Дніпропетровськ, вул. Донецьке шосе, 15/110;АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЙНИ В ДНИПРОПЕТРОВСЬКИЙ ОБЛАСТИ 18.03.2002;1;;ukr_rybak@rambler.ru
```
Формат поля saFieldsCalc - смотри сервис https://github.com/e-government-ua/i/blob/test/docs/specification.md#16-Получение-статистики-по-задачам-в-рамках-бизнес-процесса и параметр saFields 

Пример запроса:
https://test.region.igov.org.ua/wf/service/action/task/downloadTasksData?&sID_BP=dnepr_spravka_o_doxodax&bHeader=true&sID_State_BP=usertask1&sDateAt=2015-06-01&sDateTo=2015-10-01&saFieldsCalc=%22nCount=(sID_UserTask==%27usertask1%27?1:0);nTest=(sAssignedLogin==%27kermit%27?1:0)%22
Пример ответа (фрагмент):
```text;phone;bankIdfirstName;date_stop;bankIdmiddleName;sAnswer;date_start1;place_living;date_start;sQuestion;bankIdinn;bankIdPassport;area;date_stop1;saFieldQuestion;aim;initiator;bankIdlastName;email;nCount;nTest
;380970044803;ДМИТРО;;ОЛЕКСАНДРОВИЧ;;dd.MM.yyyy;Днепропетровск;;;3119325858;АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЇНИ В ДНІПРОПЕТРОВСЬКІЙ ОБЛАСТІ 18.03.2002;0463;dd.MM.yyyy;;тест;;ДУБІЛЕТ;vidokgulich@gmail.com;1.0;1.0
```

Формат поля saFieldSummary - смотри сервис https://github.com/e-government-ua/i/blob/test/docs/specification.md#16-Получение-статистики-по-задачам-в-рамках-бизнес-процесса и параметр saFieldSummary 

Пример запроса:
https://test.region.igov.org.ua/wf/service/action/task/downloadTasksData?&sID_BP=dnepr_spravka_o_doxodax&bHeader=true&sID_State_BP=usertask1&sDateAt=2015-06-01&sDateTo=2015-10-01&saFieldSummary=email;nVisites=count()
Пример ответа:
```email;nVisites
vidokgulich@gmail.com;2
kermit;1
rostislav.siryk@gmail.com;4
rostislav.siryk+igov.org.ua@gmail.com;3

```
----------------------




<a name="23_getBPForUsers">
#### 23. Получение списка бизнес процессов к которым у пользователя есть доступ (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/task/getLoginBPs?sLogin=[userId]**

* sLogin - ID пользователя

Метод возвращает json со списком бизнес процессов, к которым у пользователя есть доступ, в формате 
[
{
"sID":"[process definition key]"
"sName":"[process definition name]"
},
{
"sID":"[process definition key]"
"sName":"[process definition name]"
}
]

Принадлежность пользователя к процессу проверяется по вхождению в группы, которые могут запускать usertask-и внутри процесса, или по вхождению в группу, которая может стартовать процесс

Пример:
```
https://test.region.igov.org.ua/wf/service/action/task/getLoginBPs?sLogin=kermit
```

Пример результата

```
[{"sID":"dnepr_spravka_o_doxodax","sName":"Дніпропетровськ - Отримання довідки про доходи фіз. осіб"},{"sID":"dnepr_subsidies2","sName":"Отримання субсидії на оплату житлово-комунальних послуг2"},{"sID":"khmelnitskij_mvk_2","sName":"Хмельницький - Надання інформації, що підтверджує відсутність (наявність) земельної ділянки"},{"sID":"khmelnitskij_zemlya","sName":"Заява про наявність земельної ділянки"},{"sID":"kiev_spravka_o_doxodax","sName":"Київ - Отримання довідки про доходи фіз. осіб"},{"sID":"kuznetsovsk_mvk_5","sName":"Кузнецовськ МВК - Узгодження графіка роботи підприємства торгівлі\/обслуговування"},{"sID":"post_spravka_o_doxodax_pens","sName":"Отримання довідки про доходи (пенсійний фонд)"}]
```

<a name="24_getSheduleFlowIncludes">
#### 24. Получение расписаний включений (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/getSheduleFlowIncludes?nID_Flow_ServiceData=[flowId]**

* flowId - ID потока

Пример:
```
https://test.region.igov.org.ua/wf/service/action/flow/getSheduleFlowIncludes?nID_Flow_ServiceData=1
```

Пример результата

```
[{"sData":null,"bExclude":false,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"2010-08-01 10:10:30\"","sDateTimeTo":"\"2010-08-01 18:10:00\"","nID":20367,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}},{"sData":null,"bExclude":false,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"10:30\"","sDateTimeTo":"\"12:30\"","nID":20364,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}}]
```

<a name="25_setSheduleFlowInclude">
#### 25. Добавление/изменение расписания включений (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/setSheduleFlowInclude?nID_Flow_ServiceData=[nID_Flow_ServiceData]&sName=[sName]&sRegionTime=[sRegionTime]&sDateTimeAt=[sDateTimeAt]&sDateTimeTo=[sDateTimeTo]&saRegionWeekDay=[saRegionWeekDay]**

* nID - ИД-номер //опциональный ,если задан - редактирование
* nID_Flow_ServiceData - номер-ИД потока (обязательный если нет sID_BP)
* sID_BP - строка-ИД бизнес-процесса потока (обязательный если нет nID_Flow_ServiceData)
* sName - Строка-название ("Вечерний прием")
* sRegionTime - Строка период времени ("14:16-16-30")
* saRegionWeekDay - Массив дней недели ("su,mo,tu")
* sDateTimeAt - Строка-дата начала(на) в формате YYYY-MM-DD hh:mm:ss ("2015-07-31 19:00:00")
* sDateTimeTo - Строка-дата конца(к) в формате YYYY-MM-DD hh:mm:ss ("2015-07-31 23:00:00")
* sData - Строка с данными(выражением), описывающими формулу расписания (например: {"0 0/30 9-12 ? * TUE-FRI":"PT30M"})
* nLen - Число, определяющее длительность слота
* sLenType - Строка определяющее тип длительности слота

Пример:
```
https://test.region.igov.org.ua/wf/service/action/flow/setSheduleFlowInclude?nID_Flow_ServiceData=1&sName=Test&sRegionTime=%2210:30-11:30%22&sDateTimeAt=%222010-08-01%2010:10:30%22&sDateTimeTo=%222010-08-01%2018:10:00%22&saRegionWeekDay=%22mo,tu%22
```

Пример результата

```
{"sData":null,"bExclude":false,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"2010-08-01 10:10:30\"","sDateTimeTo":"\"2010-08-01 18:10:00\"","nID":20367,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}}
```


<a name="26_removeSheduleFlowInclude">
#### 26. Удаление расписания включений (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/removeSheduleFlowInclude?nID_Flow_ServiceData=[nID_Flow_ServiceData]&nID=[nID]**

* nID_Flow_ServiceData - номер-ИД потока (обязательный если нет sID_BP)
* sID_BP - строка-ИД бизнес-процесса потока (обязательный если нет nID_Flow_ServiceData)
* nID - ИД-номер
Ответ:
Массив объектов сущности расписаний включений

Пример:
```
https://test.region.igov.org.ua/wf/service/action/flow/removeSheduleFlowInclude?nID_Flow_ServiceData=1&nID=20367
```

Пример результата

```
{"sData":null,"bExclude":false,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"2010-08-01 10:10:30\"","sDateTimeTo":"\"2010-08-01 18:10:00\"","nID":20367,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}}
```

<a name="27_getSheduleFlowExcludes">
#### 27. Получение расписаний исключений (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/getSheduleFlowExcludes?nID_Flow_ServiceData=[flowId]***

* flowId - ID потока

Пример:
```
https://test.region.igov.org.ua/wf/service/action/flow/getSheduleFlowExcludes?nID_Flow_ServiceData=1
```

Пример результата

```
[{"sData":null,"bExclude":true,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"2010-08-01 10:10:30\"","sDateTimeTo":"\"2010-08-01 18:10:00\"","nID":20367,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}},{"sData":null,"bExclude":false,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"10:30\"","sDateTimeTo":"\"12:30\"","nID":20364,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}}]
```

<a name="28_setSheduleFlowExclude">
#### 28. Добавление/изменение расписания исключения (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/setSheduleFlowExclude?nID_Flow_ServiceData=[nID_Flow_ServiceData]&sName=[sName]&sRegionTime=[sRegionTime]&sDateTimeAt=[sDateTimeAt]&sDateTimeTo=[sDateTimeTo]&saRegionWeekDay=[saRegionWeekDay]**

* nID - ИД-номер //опциональный ,если задан - редактирование
* nID_Flow_ServiceData - номер-ИД потока (обязательный если нет sID_BP)
* sID_BP - строка-ИД бизнес-процесса потока (обязательный если нет nID_Flow_ServiceData)
* sName - Строка-название ("Вечерний прием")
* sRegionTime - Строка период времени ("14:16-16-30")
* saRegionWeekDay - Массив дней недели ("su,mo,tu")
* sDateTimeAt - Строка-дата начала(на) в формате YYYY-MM-DD hh:mm:ss ("2015-07-31 19:00:00")
* sDateTimeTo - Строка-дата конца(к) в формате YYYY-MM-DD hh:mm:ss ("2015-07-31 23:00:00")
* sData - Строка с данными(выражением), описывающими формулу расписания (например: {"0 0/30 9-12 ? * TUE-FRI":"PT30M"})
* nLen - Число, определяющее длительность слота
* sLenType - Строка определяющее тип длительности слота

Пример:
```
https://test.region.igov.org.ua/wf/service/action/flow/setSheduleFlowExclude?nID_Flow_ServiceData=1&sName=Test&sRegionTime=%2210:30-11:30%22&sDateTimeAt=%222010-08-01%2010:10:30%22&sDateTimeTo=%222010-08-01%2018:10:00%22&saRegionWeekDay=%22mo,tu%22
```

Пример результата

```
{"sData":null,"bExclude":true,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"2010-08-01 10:10:30\"","sDateTimeTo":"\"2010-08-01 18:10:00\"","nID":20367,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}}
```


<a name="29_removeSheduleFlowExclude">
#### 29. Удаление расписания исключений (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/removeSheduleFlowExclude?nID_Flow_ServiceData=[nID_Flow_ServiceData]&nID=[nID]**

* nID_Flow_ServiceData - номер-ИД потока (обязательный если нет sID_BP)
* sID_BP - строка-ИД бизнес-процесса потока (обязательный если нет nID_Flow_ServiceData)
* nID - ИД-номер
Ответ:
Массив объектов сущности расписаний исключений

Пример:
```
https://test.region.igov.org.ua/wf/service/action/flow/removeSheduleFlowExclude?nID_Flow_ServiceData=1&nID=20367
```

Пример результата

```
{"sData":null,"bExclude":true,"sName":"Test","sRegionTime":"\"10:30-11:30\"","saRegionWeekDay":"\"mo,tu\"","sDateTimeAt":"\"2010-08-01 10:10:30\"","sDateTimeTo":"\"2010-08-01 18:10:00\"","nID":20367,"nID_FlowPropertyClass":{"sPath":"org.igov.service.business.flow.handler.DefaultFlowSlotScheduler","sBeanName":"defaultFlowSlotScheduler","nID":1,"sName":"DefaultFlowSlotScheduler"}}
```


----------------------


<a name="30_workWithPatternFiles">
#### 30. Работа с файлами-шаблонами (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/object/file/getPatternFile?sPathFile=[full-path-file]&sContentType=[content-type]**
--возвращает содержимое указанного файла с указанным типом контента (если он задан).

* sPathFile - полный путь к файлу, например: folder/file.html.
* sContentType - тип контента (опционально, по умолчанию обычный текст: text/plain)

Если указанный путь неверен и файл не найден -- вернется соответствующая ошибка.

Примеры:

https://test.region.igov.org.ua/wf/service/object/file/getPatternFile?sPathFile=print//subsidy_zayava.html

ответ: вернется текст исходного кода файла-шаблона

https://test.region.igov.org.ua/wf/service/object/file/getPatternFile?sPathFile=print//subsidy_zayava.html&sContentType=text/html

ответ: файл-шаблон будет отображаться в виде html-страницы


----------------------

<a name="31_getFlowSlotTickets">
#### 31. Получение активных тикетов (описание занесено в Swagger)
</a><a href="#0_contents">↑Up</a><br/>


**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/getFlowSlotTickets?sLogin=[sLogin]&bEmployeeUnassigned=[true|false]&sDate=[yyyy-MM-dd]**
-- возвращает активные тикеты, отсортированные по startDate

* sLogin - имя пользоватеял для которого необходимо вернуть тикеты
* bEmployeeUnassigned - опциональный параметр (false по умолчанию). Если true - возвращать тикеты не заассайненые на пользователей
* sDate - опциональный параметр в формате yyyy-MM-dd. Дата за которую выбирать тикеты. При выборке проверяется startDate тикета (без учета времени. только дата). Если день такой же как и у указанное даты - такой тикет добавляется в результат.

Примеры:

https://test.region.igov.org.ua/wf/service/action/flow/getFlowSlotTickets?sLogin=kermit

```json
[{"sDateStart":"2015-07-20T15:15:00","sDateEdit":"2015-07-06T11:03:52","sTaskDate":"2015-07-30T10:03:43","sDateFinish":"2015-07-20T15:30:00","nID_FlowSlot":"6","sNameBP":"Киев - Реєстрація авто з пробігом в МРЕВ","nID_Subject":"20045","sUserTaskName":"Надання послуги: Огляд авто","nID":"20005"},{"sDateStart":"2015-07-20T15:45:00","sDateEdit":"2015-07-06T23:25:15","sTaskDate":"2015-07-06T23:27:18","sDateFinish":"2015-07-20T16:00:00","nID_FlowSlot":"7","sNameBP":"Киев - Реєстрація авто з пробігом в МРЕВ","nID_Subject":"20045","sUserTaskName":"Надання послуги: Огляд авто","nID":"20010"}]
```

https://test.region.igov.org.ua/wf/service/action/flow/getFlowSlotTickets?sLogin=kermit&bEmployeeUnassigned=true
```json
[{"sDateStart":"2015-08-03T08:00:00","sDateEdit":"2015-07-30T23:10:58","sTaskDate":"2015-07-30T23:50:07","sDateFinish":"2015-08-03T08:15:00","nID_FlowSlot":"20086","sNameBP":"Днепропетровск - Реєстрація авто з пробігом в МРЕВ","nID_Subject":"20045","sUserTaskName":"Друк держ.номерів","nID":"20151"},{"sDateStart":"2015-08-03T08:15:00","sDateEdit":"2015-07-31T21:00:56","sTaskDate":"2015-07-31T21:01:19","sDateFinish":"2015-08-03T08:30:00","nID_FlowSlot":"20023","sNameBP":"Киев - Реєстрація авто з пробігом в МРЕВ","nID_Subject":"20045","sUserTaskName":"Перевірка наявності обтяжень","nID":"20357"}]
```

https://test.region.igov.org.ua/wf/service/action/flow/getFlowSlotTickets?sLogin=kermit&bEmployeeUnassigned=true&sDate=2015-07-20
```json
[{"sDateStart":"2015-07-20T15:15:00","sDateEdit":"2015-07-06T11:03:52","sTaskDate":"2015-07-30T10:03:43","sDateFinish":"2015-07-20T15:30:00","nID_FlowSlot":"6","sNameBP":"Киев - Реєстрація авто з пробігом в МРЕВ","nID_Subject":"20045","sUserTaskName":"Надання послуги: Огляд авто","nID":"20005"},{"sDateStart":"2015-07-20T15:45:00","sDateEdit":"2015-07-06T23:25:15","sTaskDate":"2015-07-06T23:27:18","sDateFinish":"2015-07-20T16:00:00","nID_FlowSlot":"7","sNameBP":"Киев - Реєстрація авто з пробігом в МРЕВ","nID_Subject":"20045","sUserTaskName":"Надання послуги: Огляд авто","nID":"20010"}]
```


----------------------

<a name="32_getTasksByOrder">
#### 32. Получение списка ID пользовательских тасок по номеру заявки  (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/task/getTasksByOrder?nID_Order=[nID_Order]**
-- возвращает спискок ID пользовательских тасок по номеру заявки

* nID_Protected - Номер заявки, в котором, все цифры кроме последней - ID процесса в activiti. А последняя цифра - его контрольная сумма зашифрованная по алгоритму Луна.

Примеры:

https://test.region.igov.org.ua/wf/service/action/task/getTasksByOrder?nID_Order=123452

Responce status 403.
```json
{"code":"BUSINESS_ERR","message":"CRC Error"}
```

https://test.region.igov.org.ua/wf/service/action/task/getTasksByOrder?nID_Order=123451

1) Если процесса с ID 12345 и тасками нет в базе то:

Responce status 403.
```json
{"code":"BUSINESS_ERR","message":"Record not found"}
```
2) Если процесс с ID 12345 есть в базе с таской ID которой 555, то:

Responce status 200.
```json
[ 555 ]
```

<a href="#33_getStatisticServiceCounts"> </a><br/>

<a name="33_getStatisticServiceCounts">
#### 33. Получение количества записей HistoryEvent_Service для сервиса по регионам  (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: GET**

**HTTP Context: https://test.igov.org.ua/wf/service/action/event/getStatisticServiceCounts?nID_Service=[nID_Service]**

* nID_Service - ID сервиса.

Примеры:

https://test.igov.org.ua/wf/service/action/event/getStatisticServiceCounts?nID_Service=26

Результат
```json
[{"nCount":5,"nRate":0,"nTimeMinutes":"0","sName":"Київ"},{"nCount":15,"nRate":0,"nTimeHours":"2","sName":"Дніпропетровська"}]
```
--------------------------------------------------------------------------------------------------------------------------

<a name="34_upload_content_as_attach">
####34. Аплоад(upload) и прикрепление текстового файла в виде атачмента к таске Activiti (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a><br/>

**HTTP Metod: POST**

**HTTP Context: http://server:port/wf/service/object/file/upload_content_as_attachment** - Аплоад(upload) и прикрепление текстового файла в виде атачмента к таске Activiti

* nTaskId - ИД-номер таски
* sContentType - MIME тип отправляемого файла (опциоанльно) (значение по умолчанию = "text/html")
* sDescription - описание
* sFileName - имя отправляемого файла

Пример:
http://localhost:8080/wf/service/object/file/upload_content_as_attachment?nTaskId=24&sDescription=someText&sFileName=FlyWithMe.html

Ответ без ошибок:
```json
{"taskId":"38","processInstanceId":null,"userId":"kermit","name":"FlyWithMe.html","id":"25","type":"text/html;html","description":"someText","time":1433539278957,"url":null} 
ID созданного attachment - "id":"25"
```
Ответ с ошибкой:
```json
{"code":"SYSTEM_ERR","message":"Cannot find task with id 384"}
```

--------------------------------------------------------------------------------------------------------------------------

<a name="35">
#### 35. Электронная эскалация
</a><a href="#0_contents">↑Up</a>

----------------------------------------------------------------------------------------------------------------------------
 ФУНКЦИИ ЭСКАЛАЦИИ (EscalationRuleFunction)

----------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/setEscalationRuleFunction** (описание занесено в Swagger) 

 добавление/обновление записи функции эскалации

параметры: 
 + nID - ИД-номер (уникальный-автоитерируемый), опционально
 + sName - строка-название (Например "Отсылка уведомления на электронную почту"), обязательно
 + sBeanHandler - строка бина-обработчика, опционально

ответ: созданная/обновленная запись.

 - если nID не задан, то это создание записи
 - если nID задан, но его нету -- будет ошибка "403. Record not found"
 - если nID задан, и он есть -- запись обновляется

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/getEscalationRuleFunction** (описание занесено в Swagger) 

возврат одной записи функции эскалации по ее nID, если записи нету -- "403. Record not found"

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/getEscalationRuleFunctions** (описание занесено в Swagger) 

выборка всех записей функции эскалации 

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/removeEscalationRuleFunction** (описание занесено в Swagger) 

удаление записи функции эскалации по ее nID, если записи нету -- "403. Record not found"

----------------------------------------------------------------------------------------------------------------------------
 ПРАВИЛА ЭСКАЛАЦИИ (EscalationRule)

----------------------------------------------------------------------------------------------------------------------------

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/setEscalationRule** (описание занесено в Swagger) 

 добавление/обновление записи правила эскалации

параметры: 
 - nID - ИД-номер (уникальный-автоитерируемый)
 - sID_BP - ИД-строка бизнес-процесса
 - sID_UserTask - ИД-строка юзертаски бизнеспроцесса (если указана \* -- то выбираются все задачи из бизнес-процесса)
 - sCondition - строка-условие (на языке javascript )
 - soData - строка-обьект, с данными (JSON-обьект)
 - sPatternFile - строка файла-шаблона (примеры <a href="https://github.com/e-government-ua/i/blob/test/docs/specification.md#30_workWithPatternFiles">тут</a>)
 - nID_EscalationRuleFunction - ИД-номер функции эскалации

ответ: созданная/обновленная запись.

 - если nID не задан, то это создание записи
 - если nID задан, но его нету -- будет ошибка "403. Record not found"
 - если nID задан, и он есть -- запись обновляется


ПРИМЕР:
test.region.igov.org.ua/wf/service/action/escalation/setEscalationRule?sID_BP=zaporoshye_mvk-1a&sID_UserTask=*&sCondition=nElapsedDays==nDaysLimit&soData={nDaysLimit:3,asRecipientMail:['test@email.com']}&sPatternFile=escalation/escalation_template.html&nID_EscalationRuleFunction=1

ОТВЕТ:
```json
{
"sID_BP":"zaporoshye_mvk-1a",
"sID_UserTask":"*",
"sCondition":"nElapsedDays==nDaysLimit",
"soData":"{nDaysLimit:3,asRecipientMail:[test@email.com]}",
"sPatternFile":"escalation/escalation_template.html",
"nID":1008,
"nID_EscalationRuleFunction":
{"sBeanHandler":"EscalationHandler_SendMailAlert",
"nID":1,
"sName":"Send Email"}
}
```
**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/getEscalationRule** (описание занесено в Swagger) 

возврат одной записи правила эскалации по ее nID, если записи нету -- "403. Record not found"

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/removeEscalationRule**  (описание занесено в Swagger) 

удаление записи правила эскалации по ее nID, если записи нету -- "403. Record not found"

**HTTP Metod: GET**

**HTTP Context: test.region.igov.org.ua/wf/service/action/escalation/getEscalationRules** (описание занесено в Swagger) 

возвращает список всех записей правил ескалации

----------------------------------------------------------------------------------------------------------------------------

<a name="36_getTasksByText">
####36. Поиск заявок по тексту (в значениях полей без учета регистра) (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=[sFind]&sLogin=[sLogin]&bAssigned=true**
-- возвращает список ID тасок у которых в полях встречается указанный текст

* sFind - текст для поиска в полях заявки.
* sLogin - необязательный параметр. При указании выбираются только таски, которые могут быть заассайнены или заассайнены на пользователя sLogin
* bAssigned - необязательный параметр. Указывает, что нужно искать по незаассайненным таскам (bAssigned=false) и по заассайненным таскам(bAssigned=true) на пользователя sLogin

Примеры:

<a href="https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=%D0%B1%D1%83%D0%B4%D0%B8%D0%BD%D0%BA%D1%83">https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=будинк</a>

```json
["4637994","4715238","4585497","4585243","4730773","4637746"]
```

<a href="https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=%D0%B1%D1%83%D0%B4%D0%B8%D0%BD%D0%BA%D1%83&sLogin=kermit">https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=будинк&sLogin=kermit</a>


```json
["4637994","4715238","4585243","4730773","4637746"]
```

<a href="https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=%D0%B1%D1%83%D0%B4%D0%B8%D0%BD%D0%BA%D1%83&sLogin=kermit&bAssigned=false">https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=будинк&sLogin=kermit&bAssigned=false</a>


```json
["4637994","4637746"]
```

<a href="https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=%D0%B1%D1%83%D0%B4%D0%B8%D0%BD%D0%BA%D1%83&sLogin=kermit&bAssigned=true">https://test.region.igov.org.ua/wf/service/action/task/getTasksByText?sFind=будинк&sLogin=kermit&bAssigned=true</a>


```json
["4715238","4585243","4730773"]
```


<a name="37_getAccessKeyt">
####37. Получения ключа для аутентификации (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: http://server:port/wf/service/access/getAccessKey?**
-- возвращает ключ для аутентификации

* sAccessContract - контракт
* sAccessLogin - технический логин
* sData - контент по которому генерируется ключ

Пример
<a href="https://test.igov.org.ua/wf/service/access/getAccessKey?sAccessLogin=activiti-master&sAccessContract=Request&sData=/wf/service/setMessage">https://test.igov.org.ua/wf/service/access/getAccessKey?sAccessLogin=activiti-master&sAccessContract=Request&sData=/wf/service/setMessage</a>

<a name="38_setTaskQuestions">
####38. Вызов сервиса уточнения полей формы  (описание занесено в Swagger) 
</a><a href="#0_contents">↑Up</a>

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/task/setTaskQuestions?nID_Protected=[nID_Protected]&saField=[saField]&sMail=[sMail]**
сервис запроса полей, требующих уточнения у гражданина, с отсылкой уведомления
параметры:
 + nID_Protected - номер-ИД заявки (защищенный, опционально, если есть sID_Order или nID_Process)
 + sID_Order - строка-ид заявки (опционально, подробнее [тут](https://github.com/e-government-ua/i/blob/test/docs/specification.md#17_workWithHistoryEvent_Services) )
 + nID_Process - ид заявки (опционально)
 + nID_Server - ид сервера, где расположена заявка
 + saField - строка-массива полей (пример: "[{'id':'sFamily','type':'string','value':'Иванов'},{'id':'nAge','type':'long'}]")
 + sMail - строка электронного адреса гражданина
 + sHead - строка заголовка письма (опциональный, если не задан, то "Необхідно уточнити дані")
 + sBody - строка тела письма (опциональный, добавляется перед таблицей, сли не задан, то пустота)

при вызове сервиса:
 - обновляется запись HistoryEvent_Service полем значениями из soData (из saField), sToken (сгенерированый случайно 20-ти символьный код), sHead, sBody (т.е. на этоп этапе могут быть ошибки, связанные с нахождением и апдейтом <a href="https://github.com/e-government-ua/i/blob/test/docs/specification.md#17_workWithHistoryEvent_Services">обьекта события по услуге</a>)
 - отсылается письмо гражданину на указанный емейл (sMail):
  * с заголовком sHead, 
  * телом sBody 
  * перечисление полей из saField в формате таблицы: Поле / Тип / Текущее значение
  * гиперссылкой в конце типа: https://[hostCentral]/order?nID_Protected=[nID_Protected]&sToken=[sToken] 
 - находитcя на региональном портале таска, которой устанавливается в глобальную переменные sQuestion содержимое sBody и  saFieldQuestion - содержимое saField
 - сохраняется информация о действии в <a href="https://github.com/e-government-ua/i/blob/test/docs/specification.md#13_workWithHistoryEvents">Моем Журнале</a> в виде
  *  "По заявці №____ задане прохання уточнення: [sBody]"
  *  плюс перечисление полей из saField в формате таблицы Поле / Тип / Текущее значение

Пример:
https://test.region.igov.org.ua/wf/service/action/task/setTaskQuestions?nID_Protected=52302969&saField=[{'id':'bankIdfirstName','type':'string','value':'3119325858'}]&sMail=test@email

Ответы:
Пустой ответ в случае успешного обновления (и приход на указанный емейл письма описанного выше формата)

Возможные ошибки:
 - не найдена заявка (```Record not found```) или ид заявки неверное (```CRC Error```)
 - связанные с отсылкой письма, например, невалидный емейл (```Error happened when sending email```)
 - из-за некорректных входящих данных, например неверный формат saField (пример ошибки: ```Expected a ',' or ']' at 72 [character 73 line 1]```)

<a name="39_setTaskAnswer">
####39. Вызов сервиса ответа по полям требующим уточнения (описание занесено в Swagger) </a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/task/setTaskAnswer?nID_Protected=[nID_Protected]&saField=[saField]&sToken=[sToken]&sBody=[sBody]**

-- обновляет поля формы указанного процесса значениями, переданными в параметре saField
**Важно:позволяет обновлять только те поля, для которых в форме бизнес процесса не стоит атрибут writable="false"**

* nID_Protected - номер-ИД заявки (защищенный, опционально, если есть sID_Order или nID_Process)
* sID_Order - строка-ид заявки (опционально, подробнее [тут](https://github.com/e-government-ua/i/blob/test/docs/specification.md#17_workWithHistoryEvent_Services) )
* nID_Process - ид заявки (опционально)
* nID_Server - ид сервера, где расположена заявка
* saField - строка-массива полей (например: "[{'id':'sFamily','type':'string','value':'Белявцев'},{'id':'nAge','type':'long','value':35}]")
* sToken -  строка-токена. Данный параметр формируется и сохраняется в запись HistoryEvent_Service во время вызова метода setTaskQuestions
* sBody -  строка тела сообщения (опциональный параметр)

Во время выполнения метод выполняет такие действия
- Находит в сущности HistoryEvent_Service нужную запись (по nID_Protected) и сверяет токен. Eсли токен в сущности указан но не совпадает с переданным, возвращается ошибка "Token wrong". Если он в сущности не указан (null) - возвращается ошибка "Token absent".
- Находит на региональном портале таску и устанавливает в глобальную переменную sAnswer найденной таски содержимое sBody.
- Устанавливает в каждое из полей из saField новые значения
- Обновляет в сущности HistoryEvent_Service поле soData значением из saField и поле sToken значением null.
- Сохраняет информацию о действии в Мой Журнал (Текст: На заявку №____ дан ответ гражданином: [sBody])

Примеры:

https://test.region.igov.org.ua/wf/service/action/task/setTaskAnswer?nID_Protected=54352839&saField=[{%27id%27:%27bankIdinn%27,%27type%27:%27string%27,%27value%27:%271234567890%27}]&sToken=93ODp4uPBb5To4Nn3kY1

Ответы:
Пустой ответ в случае успешного обновления

Токен отсутствует
```json
{"code":"BUSINESS_ERR","message":"Token is absent"}
```

Токен не совпадает со значением в HistoryEvent_Service
```json
{"code":"BUSINESS_ERR","message":"Token is absent"}
```

Попытка обновить поле с атрибутом writable="false"
```json
{"code":"BUSINESS_ERR","message":"form property 'bankIdinn' is not writable"}
```

<a name="40_AccessServiceLoginRight">
####40. Получеение и установка прав доступа к rest сервисам</a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/access/hasAccessServiceLoginRight?sLogin=[sLogin]&sService=[sService]&sData=[sData]**
-- возвращает true - если у пользоватля с логином sLogin есть доступ к рест сервиу sService при вызове его с аргументами sData, или false - если доступа нет.

* sLogin - имя пользователя для которого проверяется доступ
* sService - строка сервиса
* sData - опциональный параметр со строкой параметров к сервису (формат передачи пока не определен). Если задан бин sHandlerBean (см. ниже) то он может взять на себя проверку допуспности сервиса для данного набора параметров.

Примеры:

https://test.region.igov.org.ua/wf/service/access/hasAccessServiceLoginRight?sLogin=SomeLogin&sService=access/hasAccessServiceLoginRight

Ответ:
```json
false
```


**HTTP Context: https://test.region.igov.org.ua/wf/service/access/getAccessServiceLoginRight?sLogin=[sLogin]**
-- возвращает список всех сервисов доступных пользователю с именем sLogin с формате JSON.

* sLogin - имя пользователя

Примеры:

https://test.region.igov.org.ua/wf/service/access/getAccessServiceLoginRight?sLogin=TestLogin

Ответ:
```json
[
    "TestService"
]
```


**HTTP Metod: POST**

**HTTP Context: https://test.region.igov.org.ua/wf/service/access/setAccessServiceLoginRight**
-- Сохраняет запись в базе, что пользователь sLogin имеет доступ к сервису sService. Существование такого пользователя и сервиса не проверяется.

Параметры:

* sLogin - имя пользователя
* sService - строка сервиса
* sHandlerBean - опцинальный параметр: имя спрингового бина реализующего интерфейс AccessServiceLoginRightHandler, который будет заниматься проверкой прав доступа для данной записи. При сохранении проверяется наличие такого бина, и если его нет - то будет выброшена ошибка.

Примеры:

https://test.region.igov.org.ua/wf/service/access/setAccessServiceLoginRight

* sLogin=SomeLogin
* sService=access/hasAccessServiceLoginRight

Ответ:
``` Status 200 ```

https://test.region.igov.org.ua/wf/service/access/setAccessServiceLoginRight?

* sLogin=SomeLogin
* sService=access/hasAccessServiceLoginRight
* sHandlerBean=WrongBean

Ответ:
```json
{
    "code": "SYSTEM_ERR",
    "message": "No bean named 'WrongBean' is defined"
}
```


**HTTP Metod: DELETE**

**HTTP Context: https://test.region.igov.org.ua/wf/service/access/removeAccessServiceLoginRight?sLogin=[sLogin]&sService=[sService]**
-- Удаляет запись из базы, что пользователь sLogin имеет доступ к сервису sService. Статус код 200 означает что запись успешно удалена. Код 304 - что такая запись не найдена.

Параметры:

* sLogin - имя пользователя
* sService - строка сервиса

Примеры:

https://test.region.igov.org.ua/wf/service/access/removeAccessServiceLoginRight?sLogin=TestLogin&sService=TestService

Ответ:
``` Status 200 ```

https://test.region.igov.org.ua/wf/service/access/removeAccessServiceLoginRight?sLogin=FakeLogin&sService=TestService

Ответ:
``` Status 304 ```

<a name="41_getFlowSlots_Department">
####41. Получение массива объектов SubjectOrganDepartment по ID бизнес процесса (описание занесено в Swagger)</a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/action/flow/getFlowSlots_Department?sID_BP=[sID_BP]**
-- возвращает массив объектов SubjectOrganDepartment для указанного Activiti BP.

* sID_BP - имя Activiti BP

Примеры:

https://test.region.igov.org.ua/wf/service/action/flow/getFlowSlots_Department?sID_BP=dnepr_dms-89

Ответ:
```json
[{"sName":"ДМС, Днепр, пр. Ильича, 3 (dnepr_dms-89,dnepr_dms-89s)","nID_SubjectOrgan":2,"sGroup_Activiti":"dnepr_dms_89_bab","nID":13},{"sName":"ДМС, Днепр, вул. Шевченко, 7 (dnepr_dms-89,dnepr_dms-89s)","nID_SubjectOrgan":2,"sGroup_Activiti":"dnepr_dms_89_zhovt","nID":14}]
```


<a name="42_getPlace">
#### 42. Работа с универсальной сущностью Place (области, районы, города, деревни) (описание занесено в Swagger) </a><br/> 
</a><a href="#0_contents">↑Up</a>

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `nID`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?nID=459

Ответ
```json
{
    "nLevelArea": null,
    "nLevel": 0,
    "o": {
        "nID": 459,
        "sName": "Недригайлівський район/смт Недригайлів",
        "nID_PlaceType": 2,
        "sID_UA": "5923500000",
        "sNameOriginal": ""
    },
    "a": [
        {
            "nLevelArea": null,
            "nLevel": 1,
            "o": {
                "nID": 460,
                "sName": "Недригайлів",
                "nID_PlaceType": 4,
                "sID_UA": "5923555100",
                "sNameOriginal": ""
            },
            "a": []
        }
    ]
}
```
**Примечание**: по умолчанию возвращаются иерархия с ограниченным уровнем вложенности детей (поле `nDeep`, по умолчанию равно 1).

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `nID`) и количества уровней вниз (параметр `nDeep`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?nID=459&nDeep=4

Ответ:
```json
{
    "nLevelArea": null,
    "nLevel": 0,
    "o": {
        "nID": 459,
        "sName": "Недригайлівський район/смт Недригайлів",
        "nID_PlaceType": 2,
        "sID_UA": "5923500000",
        "sNameOriginal": ""
    },
    "a": [
        {
            "nLevelArea": null,
            "nLevel": 1,
            "o": {
                "nID": 460,
                "sName": "Недригайлів",
                "nID_PlaceType": 4,
                "sID_UA": "5923555100",
                "sNameOriginal": ""
            },
            "a": [
                {
                    "nLevelArea": null,
                    "nLevel": 2,
                    "o": {
                        "nID": 458,
                        "sName": "Вільшана",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923584401",
                        "sNameOriginal": ""
                    },
                    "a": []
                },
                {
                    "nLevelArea": null,
                    "nLevel": 2,
                    "o": {
                        "nID": 461,
                        "sName": "Вакулки",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923555101",
                        "sNameOriginal": ""
                    },
                    "a": []
                },
                {
                    "nLevelArea": null,
                    "nLevel": 2,
                    "o": {
                        "nID": 462,
                        "sName": "Віхове",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923555102",
                        "sNameOriginal": ""
                    },
                    "a": []
                }
            ]
        }
    ]
}
```

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `sUA_ID`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?sID_UA=5923500000

```json
{
    "nLevelArea": null,
    "nLevel": 0,
    "o": {
        "nID": 459,
        "sName": "Недригайлівський район/смт Недригайлів",
        "nID_PlaceType": 2,
        "sID_UA": "5923500000",
        "sNameOriginal": ""
    },
    "a": [
        {
            "nLevelArea": null,
            "nLevel": 1,
            "o": {
                "nID": 460,
                "sName": "Недригайлів",
                "nID_PlaceType": 4,
                "sID_UA": "5923555100",
                "sNameOriginal": ""
            },
            "a": []
        }
    ]
}
```

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `sUA_ID`) и количества уровней вниз (параметр `nDeep`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?sID_UA=5923500000&nDeep=3

Ответ:
```json
{
    "nLevelArea": null,
    "nLevel": 0,
    "o": {
        "nID": 459,
        "sName": "Недригайлівський район/смт Недригайлів",
        "nID_PlaceType": 2,
        "sID_UA": "5923500000",
        "sNameOriginal": ""
    },
    "a": [
        {
            "nLevelArea": null,
            "nLevel": 1,
            "o": {
                "nID": 460,
                "sName": "Недригайлів",
                "nID_PlaceType": 4,
                "sID_UA": "5923555100",
                "sNameOriginal": ""
            },
            "a": [
                {
                    "nLevelArea": null,
                    "nLevel": 2,
                    "o": {
                        "nID": 458,
                        "sName": "Вільшана",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923584401",
                        "sNameOriginal": ""
                    },
                    "a": []
                },
                {
                    "nLevelArea": null,
                    "nLevel": 2,
                    "o": {
                        "nID": 461,
                        "sName": "Вакулки",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923555101",
                        "sNameOriginal": ""
                    },
                    "a": []
                },
                {
                    "nLevelArea": null,
                    "nLevel": 2,
                    "o": {
                        "nID": 462,
                        "sName": "Віхове",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923555102",
                        "sNameOriginal": ""
                    },
                    "a": []
                }
            ]
        }
    ]
}
```

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `nID` или `sUA_ID`) c фильтрацией по типу (nID_PlaceType)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?nID=459&nDeep=3&nID_PlaceType=5

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `nID` или `sUA_ID`) c фильтрацией по региону (bArea)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?nID=459&bArea=false&nDeep=3

**_Получить иерархию объектов вниз начиная с указанного узла (параметр `nID` или `sUA_ID`) c фильтрацией по корневому региону (bRoot)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlacesTree?nID=459&bRoot=false&nDeep=3

**_Получить иерархию объектов вверх начиная с указанного узла (параметр `nID`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlace?nID=462

_Ответ_
```json
{
    "nLevelArea": null,
    "nLevel": 0,
    "o": {
        "nID": 462,
        "sName": "Віхове",
        "nID_PlaceType": 5,
        "sID_UA": "5923555102",
        "sNameOriginal": ""
    },
    "a": []
}
```
**Примечание**: по умолчанию возвращаются иерархия с ограниченным уровнем (только 1 уровень)

**_Получить иерархию объектов вверх начиная с указанного узла (параметр `nID`). Для активации выборки по полной иерархии необходимо указать параметр `bTree`._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlace?nID=462&bTree=true

_Ответ (возвращает иерархию объектов снизу вверх, напр. от деревни к району/области)_
```json
{
    "nLevelArea": null,
    "nLevel": 2,
    "o": {
        "nID": 459,
        "sName": "Недригайлівський район/смт Недригайлів",
        "nID_PlaceType": 2,
        "sID_UA": "5923500000",
        "sNameOriginal": ""
    },
    "a": [
        {
            "nLevelArea": null,
            "nLevel": 1,
            "o": {
                "nID": 460,
                "sName": "Недригайлів",
                "nID_PlaceType": 4,
                "sID_UA": "5923555100",
                "sNameOriginal": ""
            },
            "a": [
                {
                    "nLevelArea": null,
                    "nLevel": 0,
                    "o": {
                        "nID": 462,
                        "sName": "Віхове",
                        "nID_PlaceType": 5,
                        "sID_UA": "5923555102",
                        "sNameOriginal": ""
                    },
                    "a": []
                }
            ]
        }
    ]
}
```

**_Получить иерархию объектов вверх начиная с указанного узла (параметр `sUA_ID`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlace?sID_UA=5923555102


**_Получить иерархию объектов вверх начиная с указанного узла (параметр `sUA_ID`). Для активации выборки по полной иерархии необходимо указать параметр `bTree`._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlace?sID_UA=5923555102&bTree=true

**_Вставить новый объект Place._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/setPlace?sName=child_of_462&nID_PlaceType=5&sID_UA=90005000462&sNameOriginal=5000_462_child

**Результат** 

1.  HTTP code = 200 OK
  
2.  GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceEntity?sID_UA=90005000462 должен вернуть вашу сущность.
```json
{
    "sID_UA": "90005000462",
    "nID": 22830,
    "sName": "child_of_462",
    "nID_PlaceType": 5,
    "sNameOriginal": "5000_462_child"
}
```
**Примечание**: Первичный ключ (параметр `nID`) мы не указываем, т.к. хибернейт обязан сам генерировать PK

**_Обновить объект Place (параметр для поиска `sID_UA`)._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/setPlace?sName=child_of_462&nID_PlaceType=5&sID_UA=90005000462&sNameOriginal=5000_462_child

**Результат** 

1.  HTTP code = 200 OK

2.  GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceEntity?sID_UA=90005000462 должен вернуть вашу сущность с обновленными параметрами:
```json
{
    "sID_UA": "90005000462",
    "nID": 22830,
    "sName": "child_of_462",
    "nID_PlaceType": 5,
    "sNameOriginal": "5000_462_child"
}
```

**_Обновить объект Place (параметр для поиска `nID`, PK)._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/setPlace?sName=The_child_of_462&nID_PlaceType=5&sNameOriginal=50000_462_child&nID=22830&sID_UA=90005000462

**Результат** 

1.  HTTP code = 200 OK

2.  GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceEntity?nID=22830 должен вернуть вашу сущность с обновленными параметрами:
```json
{
    "sID_UA": "90005000462",
    "nID": 22830,
    "sName": "child_of_462",
    "nID_PlaceType": 5,
    "sNameOriginal": "5000_462_child"
}
```

**_Удалить объект Place по первичному ключу (параметр `nID`)._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/removePlace?nID=22830

**Результат** 

1.  HTTP code = 200 OK

2.  GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceEntity?nID=22830 должен вернуть cообщение об ошибке:
```json
{
    "code": "SYSTEM_ERR",
    "message": "Entity with id=22830 does not exist"
}
```

**_Удалить объект Place по уникальному UA id (параметр `sID_UA`)._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/removePlace?sID_UA=90005000462

**Результат** 

1.  HTTP code = 200 OK

2.  GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceEntity?nID=22830 должен вернуть cообщение об ошибке:
```json
{
    "code": "SYSTEM_ERR",
    "message": "Entity with sID_UA='90005000462' not found"
}
```

**_Получить тип места (область)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlaceTypes?bArea=true&bRoot=true

**Ответ** 
```json
[
    {
        "bArea": true,
        "bRoot": true,
        "nID": 1,
        "sName": "Область",
        "nOrder": null
    }
]
```

**_Получить тип места (район)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlaceTypes?bArea=true&bRoot=false
 
**Ответ** 
```json
[
    {
        "bArea": true,
        "bRoot": false,
        "nID": 2,
        "sName": "Район",
        "nOrder": null
    }
]
```

**_Получить тип места (ПГТ, город, село)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlaceTypes?bArea=false&bRoot=false

**Ответ** 
```json
[
    {
        "bArea": false,
        "bRoot": false,
        "nID": 3,
        "sName": "Город",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 4,
        "sName": "ПГТ",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 5,
        "sName": "Село",
        "nOrder": null
    }
]
```
**_Получить тип места (как сущность) по первичному ключу (параметр `nID`)._**

**HTTP Metod: GET**

https://test.igov.org.ua/wf/service/object/place/getPlaceType?nID=1

**Ответ** 
```json
[
    {
        "bArea": true,
        "bRoot": true,
        "nID": 1,
        "sName": "Область",
        "nOrder": null
    }
]
```

**_Cоздать новый тип места._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/setPlaceType?sName=Type_1&nOrder=2&bArea=false&bRoot=false

**Результат**

1. HTTP code = 200 OK

2. GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceTypes?bArea=false&bRoot=false должен вернуть масив с вашей сущностью:
```json
[
    {
        "bArea": false,
        "bRoot": false,
        "nID": 3,
        "sName": "Город",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 4,
        "sName": "ПГТ",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 5,
        "sName": "Село",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 23418,
        "sName": "Type_1",
        "nOrder": 2
    }
]
```

**_Удалить тип места по первичному ключу (`nID`)._**

**HTTP Metod: POST**

https://test.igov.org.ua/wf/service/object/place/removePlaceType?nID=23417

**Результат** 

1. HTTP code = 200 OK

2. GET запрос по адресу https://test.igov.org.ua/wf/service/object/place/getPlaceTypes?bArea=false&bRoot=false должен вернуть масив без вашей сущности:
```json
[
    {
        "bArea": false,
        "bRoot": false,
        "nID": 3,
        "sName": "Город",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 4,
        "sName": "ПГТ",
        "nOrder": null
    },
    {
        "bArea": false,
        "bRoot": false,
        "nID": 5,
        "sName": "Село",
        "nOrder": null
    }
]
```


<a name="43_check_attachment_sign">
####43. Проверка ЭЦП на атачменте(файл) таски Activiti (описание занесено в Swagger)</a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/object/file/check_attachment_sign?nID_Task=[nID_Task]&nID_Attach=[nID_Attach]**
-- возвращает json объект описывающий ЭЦП файла-аттачмента.

* nID_Task - id таски Activiti BP
* nID_Attach - id атачмента приложеного к таске

Примеры:

https://test.region.igov.org.ua/wf/service/object/file/check_attachment_sign?nID_Task=7315073&nID_Attach=7315075

Ответ:
```json
{"state":"ok","customer":{"inn":"1436057000","fullName":"Сервіс зберігання сканкопій","signatureData":{"name":"АЦСК ПАТ КБ «ПРИВАТБАНК»","serialNumber":"0D84EDA1BB9381E80400000079DD02004A710800","timestamp":"29.10.2015 13:45:33","code":true,"desc":"ПІДПИС ВІРНИЙ","dateFrom":"13.08.2015 11:24:31","dateTo":"12.08.2016 23:59:59","sn":"UA-14360570-1"},"organizations":[{"type":"edsOwner","name":"ПАТ КБ «ПРИВАТБАНК»","mfo":"14360570","position":"Технологічний сертифікат","ownerDesc":"Співробітник банку","address":{"type":"factual","state":"Дніпропетровська","city":"Дніпропетровськ"}},{"type":"edsIsuer","name":"ПУБЛІЧНЕ АКЦІОНЕРНЕ ТОВАРИСТВО КОМЕРЦІЙНИЙ БАНК «ПРИВАТБАНК»","unit":"АЦСК","address":{"type":"factual","state":"Дніпропетровська","city":"Дніпропетровськ"}}]}}
```

Ответ для несуществующей таски (nID_Task):
```json
{"code":"SYSTEM_ERR","message":"ProcessInstanceId for taskId '7315070' not found."}
```

Ответ для несуществующего атачмента (nID_Attach):
```json
{"code":"SYSTEM_ERR","message":"Attachment for taskId '7315073' not found."}
```

Ответ для атачмента который не имеет наложеной ЭЦП:
```json
{}
```

<a name="44_check_file_from_redis_sign">
####44. Проверка ЭЦП на файле хранящемся в Redis (описание занесено в Swagger) </a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/object/file/check_file_from_redis_sign?sID_File_Redis=[sID_File_Redis]**
-- возвращает json объект описывающий ЭЦП файла.

* sID_File_Redis - key по которому можно получить файл из хранилища Redis.


Примеры:

https://test.region.igov.org.ua/wf/service/object/file/check_file_from_redis_sign?sID_File_Redis=d2993755-70e5-409e-85e5-46ba8ce98e1d

Ответ json описывающий ЭЦП:
```json
{"state":"ok","customer":{"inn":"1436057000","fullName":"Сервіс зберігання сканкопій","signatureData":{"name":"АЦСК ПАТ КБ «ПРИВАТБАНК»","serialNumber":"0D84EDA1BB9381E80400000079DD02004A710800","timestamp":"29.10.2015 13:45:33","code":true,"desc":"ПІДПИС ВІРНИЙ","dateFrom":"13.08.2015 11:24:31","dateTo":"12.08.2016 23:59:59","sn":"UA-14360570-1"},"organizations":[{"type":"edsOwner","name":"ПАТ КБ «ПРИВАТБАНК»","mfo":"14360570","position":"Технологічний сертифікат","ownerDesc":"Співробітник банку","address":{"type":"factual","state":"Дніпропетровська","city":"Дніпропетровськ"}},{"type":"edsIsuer","name":"ПУБЛІЧНЕ АКЦІОНЕРНЕ ТОВАРИСТВО КОМЕРЦІЙНИЙ БАНК «ПРИВАТБАНК»","unit":"АЦСК","address":{"type":"factual","state":"Дніпропетровська","city":"Дніпропетровськ"}}]}}
```

Ответ для несуществующего ключа (sID_File_Redis):
```json
{"code":"SYSTEM_ERR","message":"File with sID_File_Redis 'd2993755-70e5-409e-85e5-46ba8ce98e1e' not found."}
```

Ответ для файла который не имеет наложеной ЭЦП:
```json
{}
```

<a name="45_getServer">
####45. Получение информации о сервере  (описание занесено в Swagger) </a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.region.igov.org.ua/wf/service/subject/getServer?nID=[nID]**
-- возвращает json представление сущности Server, которая содержит информацию о сервере.

* nID - nID сервера.

Примеры:

https://test.region.igov.org.ua/wf/service/subject/getServer?nID=0

Ответ:
```json
{
    "sID": "Common_Region",
    "sType": "Region",
    "sURL_Alpha": "https://test.region.igov.org.ua/wf",
    "sURL_Beta": "https://test-version.region.igov.org.ua/wf",
    "sURL_Omega": "https://master-version.region.igov.org.ua/wf",
    "sURL": "https://region.igov.org.ua/wf",
    "nID": 0
}
```

https://test.region.igov.org.ua/wf/service/subject/getServer?nID=-1

Ответ:

HTTP Status: 500 (internal server error)

```json
{
    "code": "BUSINESS_ERR",
    "message": "Record not found"
}
```

<a name="46_getLastTaskHistory">
####46. Проверка наличия task определенного Бизнес процесса (БП), указанного гражданина (описание занесено в Swagger) </a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: http://test.igov.org.ua/wf/service/action/task/event/getLastTaskHistory?nID_Subject=nID_Subject&nID_Service=nID_Service&sID_UA=sID_UA**
--  возвращает сущность HistoryEvent_Service или ошибку Record not found.

* nID_Subject - номер-ИД субьекта (переменная обязательна)
* nID_Service - номер-ИД услуги  (переменная обязательна)
* sID_UA - строка-ИД места Услуги  (переменная обязательна)


Примеры:

http://test.igov.org.ua/wf/service/action/task/event/getLastTaskHistory?nID_Subject=2&nID_Service=1&sID_UA=1200000000

Ответ, если запись существует (HTTP status Code: 200 OK):
```json
{
  "sID": "2",
  "nID_Task": 2,
  "nID_Subject": 2,
  "sStatus": "processing",
  "sID_Status": "заявка в обработке",
  "sDate": null,
  "nID_Service": 1,
  "nID_Region": 1,
  "sID_UA": "1200000000",
  "nRate": 0,
  "soData": "[]",
  "sToken": "",
  "sHead": "",
  "sBody": "",
  "nTimeMinutes": 0,
  "sID_Order": "0-22",
  "nID_Server": 0,
  "nID_Protected": null,
  "nID": 8
}
```

Ответ, если записи не существует. (HTTP status Code: 500 Internal Server Error):
```json
{
  "code": "BUSINESS_ERR",
  "message": "Record not found"
}
```

<a name="47_getStartFormByTask">
####47. Получение полей стартовой формы по: ИД субьекта, ИД услуги, ИД места Услуги.  (описание занесено в Swagger) </a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: https://test.igov.org.ua/wf/service/action/task/getStartFormByTask_Central?nID_Subject=[nID_Subject]&nID_Service=[nID_Service]&sID_UA=[sID_UA]&nID_Server=[nID_Server]**
--  возвращает JSON содержащий поля стартовой формы процесса, процесс находится на основании ИД таски полученой из сущности HistoryEvent_Service. На основании HistoryEvent_Service определяется региональный сервер откуда нужно получить поля формы и собственно ИД таски.

* nID_Subject - номер-ИД субьекта (переменная обязательна)
* nID_Service - номер-ИД услуги  (переменная обязательна)
* sID_UA - строка-ИД места Услуги  (переменная обязательна)
* nID_Server - номер-ИД сервера опциональный, по умолчанию 0


Примеры:

https://test.igov.org.ua/wf/service/action/task/getStartFormByTask_Central?nID_Subject=2&nID_Service=1&sID_UA=1200000000

Ответ, если запись существует (HTTP status Code: 200 OK):
```json
{waterback="--------------------",phone="380979362996",date_from="01/01/2014",bankIdbirthDay="27.05.1985",notice2="Я та особи, які зареєстровані (фактично проживають) у житловому приміщенні/будинку, даємо згоду на обробку персональних даних про сім’ю, доходи, майно, що необхідні для призначення житлової субсидії, та оприлюднення відомостей щодо її призначення.",house="--------------------",garbage="--------------------",waterback_notice="",garbage_number="",floors="10",name_services="--------------------",date_to="30/12/2014",date3="",date2="",electricity="--------------------",garbage_name="",date1="",place_type="2",bankIdfirstName="ДМИТРО",declaration="--------------------",waterback_name="",electricity_notice="",bankIdinn="3119325858",house_name="",gas="--------------------",house_number="",subsidy="1",email="dmitrij.zabrudskij@privatbank.ua",warming="--------------------",hotwater_notice="",org0="Назва організації",org1="",electricity_number="123456",org2="",org3="",warming_name="",place_of_living="Дніпропетровська, Дніпропетровськ, пр. Героїв, 17, кв 120",fio2="",fio3="",total_place="68",garbage_notice="",fio1="",chapter1="--------------------",bankIdmiddleName="ОЛЕКСАНДРОВИЧ",gas_name="",bankIdPassport="АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЇНИ В ДНІПРОПЕТРОВСЬКІЙ ОБЛАСТІ 18.03.2002",warming_place="45",passport3="",gas_number="",passport2="",electricity_name="коммуна",area="samar",house_notice="",bankIdlastName="ДУБІЛЕТ",card1="",card3="",coolwater_number="",card2="",warming_notice="",hotwater_name="",income0="attr9",coolwater="--------------------",gas_notice="",overload="hxhxfhfxhfghg",warming_number="",income3="attr0",income1="attr0",income2="attr0",passport1="",coolwater_notice="",sBody_1="null",hotwater="--------------------",coolwater_name="",waterback_number="",man1="",hotwater_number="",sBody_2="null",comment="null",decision="null",selection="attr1"}
```

Ответ, если записи не существует. (HTTP status Code: 500 Internal Server Error):
```json
{
  "code": "BUSINESS_ERR",
  "message": "Record not found"
}
```

<a name="48_getStartFormData">
####48. Получение полей стартовой формы по ID таски.  (описание занесено в Swagger)</a><br/> 
</a><a href="#0_contents">↑Up</a>

**HTTP Metod: GET**

**HTTP Context: http://test.region.igov.org.ua/wf/service/action/task/getStartFormData?nID_Task=[nID_Task]**
--  возвращает JSON содержащий поля стартовой формы процесса.

* nID_Task - номер-ИД таски, для которой нужно найти процесс и вернуть поля его стартовой формы.


Примеры:

http://test.region.igov.org.ua/wf/service/action/task/getStartFormData?nID_Task=5170256

Ответ, если запись существует (HTTP status Code: 200 OK):
```json
{waterback="--------------------",phone="380979362996",date_from="01/01/2014",bankIdbirthDay="27.05.1985",notice2="Я та особи, які зареєстровані (фактично проживають) у житловому приміщенні/будинку, даємо згоду на обробку персональних даних про сім’ю, доходи, майно, що необхідні для призначення житлової субсидії, та оприлюднення відомостей щодо її призначення.",house="--------------------",garbage="--------------------",waterback_notice="",garbage_number="",floors="10",name_services="--------------------",date_to="30/12/2014",date3="",date2="",electricity="--------------------",garbage_name="",date1="",place_type="2",bankIdfirstName="ДМИТРО",declaration="--------------------",waterback_name="",electricity_notice="",bankIdinn="3119325858",house_name="",gas="--------------------",house_number="",subsidy="1",email="dmitrij.zabrudskij@privatbank.ua",warming="--------------------",hotwater_notice="",org0="Назва організації",org1="",electricity_number="123456",org2="",org3="",warming_name="",place_of_living="Дніпропетровська, Дніпропетровськ, пр. Героїв, 17, кв 120",fio2="",fio3="",total_place="68",garbage_notice="",fio1="",chapter1="--------------------",bankIdmiddleName="ОЛЕКСАНДРОВИЧ",gas_name="",bankIdPassport="АМ765369 ЖОВТНЕВИМ РВ ДМУ УМВС УКРАЇНИ В ДНІПРОПЕТРОВСЬКІЙ ОБЛАСТІ 18.03.2002",warming_place="45",passport3="",gas_number="",passport2="",electricity_name="коммуна",area="samar",house_notice="",bankIdlastName="ДУБІЛЕТ",card1="",card3="",coolwater_number="",card2="",warming_notice="",hotwater_name="",income0="attr9",coolwater="--------------------",gas_notice="",overload="hxhxfhfxhfghg",warming_number="",income3="attr0",income1="attr0",income2="attr0",passport1="",coolwater_notice="",sBody_1="null",hotwater="--------------------",coolwater_name="",waterback_number="",man1="",hotwater_number="",sBody_2="null",comment="null",decision="null",selection="attr1"}
```

Ответ, если записи не существует. (HTTP status Code: 500 Internal Server Error):
```json
{
  "code": "BUSINESS_ERR",
  "message": "Record not found"
}
```

<a name="49">
####49. Субьекты-органы - Филиалы - Таможенные (описание занесено в Swagger)</a><br/> 
</a><a href="#0_contents">↑Up</a>

----------------------

**HTTP Context: https://server:port/wf/service/subject/getSubjectOrganJoinTax**

**Method: GET**

Возвращает весь список таможенных органов (залит справочник согласно <a href="http://sfs.gov.ua/baneryi/mitne-oformlennya/subektam-zed/reestri-ta-klasifikatori/klasifikatori/vidomchi-klasifikatori-informatsii-z-pi/62554.html">Державна фіскальна служба України. Офіційний портал</a>) 
                                                                                                                                                                                                                                  

Пример: https://test.igov.org.ua/wf/service/subject/getSubjectOrganJoinTax

----------------------

**HTTP Context: https://server:port/wf/service/subject/setSubjectOrganJoinTax**

**Method: GET**

Апдейтит элемент (если задан один из уникальных ключей) или вставляет (если не задан nID), и отдает экземпляр нового объекта. (описание занесено в Swagger)
 
Параметры:
 * nID - ИД-номер, идентификатор записи
 * sID_UA - ИД-номер Код, в Украинском классификаторе (уникальное)
 * sName_UA - название на украинском (строка до 190 символов)
 
Если нет ни одного параметра  возвращает ошибку ```403. All args are null!```
Если есть один из уникальных ключей, но запись не найдена -- ошибка ```403. Record not found!```
Если кидать "новую" запись с одним из уже существующих ключей sID_UA -- то обновится существующая запись по ключу sID_UA, если будет дублироваться другой ключ -- ошибка ```403. Could not execute statement``` (из-за уникальных констрейнтов)

----------------------

**HTTP Context: https://server:port/wf/service/subject/removeSubjectOrganJoinTax**

**Method: GET**
 
Удаляет обьект по одному из двух ключей (nID, sID_UA) или кидает ошибку ```403. Record not found!```.
 
Параметры:
 * nID - ИД-номер, идентификатор записи
 * sID_UA - ИД-номер Код, в Украинском классификаторе (уникальное)

<a name="50">
####50. Работа с валютами  (описание занесено в Swagger)</a><br/>
</a><a href="#0_contents">↑Up</a>

----------------------

**HTTP Context: https://server:port/wf/service/finance/getCurrencies**

**Method: GET**

Возвращает список валют, подпадающих под параметры

<a href="http://search.ligazakon.ua/l_doc2.nsf/link1/FIN14565.html">Источник данных</a>

Параметры:

* <b>sID_UA</b> - ИД-номер Код, в украинском классификаторе (уникальный, опциональный)
* <b>sName_UA</b> - название на украинском (уникальный, опциональный)
* <b>sName_EN</b> - название на английском (уникальный, опциональный)

Пример запроса: https://test.igov.org.ua/wf/service/finance/getCurrencies?sID_UA=004

Пример ответа:
```json
{
    "sID_UA"   : "004",
    "sName_UA" : "Афґані",
    "sName_EN" : "Afghani",
    "nID"      : 1
}
```

----------------------

**HTTP Context: https://server:port/wf/service/finance/setCurrency**

**Method: GET**

обновляет запись (если задан один из параметров: nID, sID_UA; и по нему найдена запись) или вставляет (если не задан nID), и отдает экземпляр нового объекта

<a href="http://search.ligazakon.ua/l_doc2.nsf/link1/FIN14565.html">Источник данных</a>

Параметры:

* <b>nID</b> - внутренний ИД-номер (уникальный; опциональный, если sID_UA задан и по нему найдена запись)
* <b>sID_UA</b> - ИД-номер Код, в украинском классификаторе (уникальный; опциональный, если nID задан и по нему найдена запись)
* <b>sName_UA</b> - название на украинском (уникальный; опциональный, если nID задан и по нему найдена запись)
* <b>sName_EN</b> - название на английском (уникальный; опциональный, если nID задан и по нему найдена запись)

Пример добавления записи:<br/>
https://test.igov.org.ua/wf/service/finance/setCurrency?sID_UA=050&sName_UA=Така&sName_EN=Taka
Пример обновления записи:<br/>
https://test.igov.org.ua/wf/service/finance/setCurrency?sID_UA=050&sName_UA=Така

----------------------

**HTTP Context: https://server:port/wf/service/finance/removeCurrency**

**Method: GET**

удаляет элемент по обязательно заданному одному из параметров

<a href="http://search.ligazakon.ua/l_doc2.nsf/link1/FIN14565.html">Источник данных</a>

Параметры:

* <b>nID</b> - внутренний ИД-номер (уникальный; опциональный, если sID_UA задан и по нему найдена запись)
* <b>sID_UA</b> - ИД-номер Код, в украинском классификаторе (уникальный; опциональный, если nID задан и по нему найдена запись)

Пример запроса: https://test.igov.org.ua/wf/service/finance/removeCurrency?sID_UA=050
