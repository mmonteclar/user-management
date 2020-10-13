curl -X PUT \
  http://127.0.0.1:8080/user/add \
  -H 'authorization: Basic MTExOjExMQ==' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 253eca47-2b4a-6b04-6291-b86b78340af4' \
  -d '{
	"userId": 888,
	"title": "Mrs",
	"firstn": "Monkey",
	"lastname": "Go",
	"gender": "Female",
	"address": {
		"street": "Francis Road",
		"city": "NSW",
		"state": "Sydney",
		"postcode": 2000
	}
}'