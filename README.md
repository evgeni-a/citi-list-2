# CITY LIST 
Test task application

## QUICK START
run in terminal:

~~~
docker-compose up -d
~~~
and navigate to the URL
[http://localhost:8082/](http://localhost:8082/)

please use next demo credentials: test/test

if you don't have Docker please follow [instructions]([http://localhost:8082/](http://localhost:8082/))


### [Swagger](http://localhost:8082/swagger-ui/index.html) 


## Build own docker images

### Build API
replace following 'docker.io/evgenianchuk' with your's registry
~~~
cd api
./gradlew bootBuildImage
docker push docker.io/evgenianchuk/citylist-api:latest
~~~

### Build UI
!!!Attention!!! for producion build generate new certificates (see section below)
~~~
docker build -t docker.io/evgenianchuk/citylist-front:latest frontend
docker push docker.io/evgenianchuk/citylist-front:latest
~~~

## Run locally for debug

### UI
if you want to change frontend port, please specify correct backend URL
~~~
process.env.VUE_APP_API_URL in vue.config.js 
~~~
and perform in terminal
~~~
cd frontend
npm run serve
~~~

### API
run SpringBootApplication with class CityListApplication 

## Generate RSA certificates for JWT authentication:

These certificates are used for issuing and verification of JWT tokens.
You need to generate them for both main application and tests.

### For main application:
~~~
cd ./api/src/main/resources/certs

# Remove existing private keys (if any)
rm -f private*.pem

# Generate RSA private key
openssl genpkey -algorithm RSA -out private_orig.pem -pkeyopt rsa_keygen_bits:2048

# Generate public key from private key
openssl rsa -pubout -in private_orig.pem -out public.pem

# Convert private key to PKCS8 format without password
openssl pkcs8 -topk8 -in private_orig.pem -nocrypt -out private.pem

# Verify files are created
ls -la *.pem
~~~

### For tests:
~~~
# Create test certificates directory
mkdir -p ./api/src/test/resources/test-certs

# Copy generated keys to test directory
cp ./api/src/main/resources/certs/public.pem ./api/src/test/resources/test-certs/
cp ./api/src/main/resources/certs/private.pem ./api/src/test/resources/test-certs/
~~~

Note: The old `generate_keys.sh` script requires manual password input which makes it less suitable for automated builds.

