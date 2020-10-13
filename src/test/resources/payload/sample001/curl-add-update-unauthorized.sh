curl -X PUT \
  http://127.0.0.1:8080/user/add \
  -H 'authorization: Basic MTE6MTEx' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 28e03918-ab9a-cde5-7cca-ec312f5aa0bb' \
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