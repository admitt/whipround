# whipround
#sixhackathon demo

http://localhost:8080/h2-console
jdbc:h2:mem:testdb
sa

# API

## Registration(steps order)

#### */signup/+4100000XX*

Method: **GET**

Response:
```
{"smscode":5470,"type":"register"}
```

#### */signin/register/5470*
Method: **POST**

Response: `http 200`

#### */register*

Method: **POST**

Request body:

```
{   "firstname": "name",   "lastname": "surname",   "email": "name@google.ch",   "pin": "1234" }
```
Response:
```
 {"id":999}
```

## Login(steps order)

#### */signup/+4100000XX*

Method: **GET**

Response:
```
{"smscode":8780,"type":"login"}
```

#### */login/8780*

Method: **POST**

Response:
```
 {"id":999}
```

## Group account

#### */account/create*

Method: **POST**

Request params:
```
reason String – account creation reason
manager Integer – manager id
```
Response: `http 200`

#### */account/{account}/add/member/{member}*

Method: **POST**

Request params:
```
account Integer – account id
member Integer – member id
```

Response: `http 200`

#### */account/add/transaction*

Method: **POST**

Request params:
```
amount Decimal - positive/negative transaction amount
member Integer - member id
group Integer - group id
```

Response: `http 200`

#### */account/balance*

Method: **GET**

Request params:
```
group Integer - group id
```

Response: `200.99`

#### */account/members*

Method: **GET**

Request params:
```
group Integer - group id
```

Response:
```
200.99
```

#### */account/transactions*

Method: **POST**

Request params:
```
group Integer - group id
```

Response:
```
[{"id":1500,"amount":100.00,"currencyCode":"CHF","member":{"id":999,"firstName":"Teine","lastName":"laps"}},{"id":1600,"amount":-50.00,"currencyCode":"CHF","member":{"id":999,"firstName":"Teine","lastName":"laps"}}]
```

## Member
#### */member/groups*

Request params:
```
member Integer - member id
```

Response:
```
[{"id":777,"manager":{"id":888,"firstName":"Esimene","lastName":"Laps"},"reason":"trip to Zurich"}]
```

## Report
#### */report/map?group={group id}
Method: **GET**
Request params:
```
group Integer - group id
```
Response:
```
google map image/jpg with track transactions
```

#### */report/link?group={group id}
Method: **GET**
Request params:
```
group Integer - group id
```
Response:
```
google map link with trrack transactions
```