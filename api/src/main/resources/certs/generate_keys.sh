# Private key
openssl genpkey -algorithm RSA -out private_orig.pem -pkeyopt rsa_keygen_bits:2048

# Public key
openssl rsa -pubout -in private_orig.pem -out public.pem

# Private key in pkcs8 format
openssl pkcs8 -topk8 -in private_orig.pem -out private.pem

## nocrypt (Private key does have no password)
openssl pkcs8 -topk8 -in private_orig.pem -nocrypt -out private.pem