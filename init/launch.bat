#!/bin/bash
# Démarrer le backend Spring Boot
cd backend
./mvnw spring-boot:run &
BACK_PID=$!
echo "Backend lancé (PID $BACK_PID)"

# Démarrer le frontend React
cd ../frontend
npm start &
FRONT_PID=$!
echo "Frontend lancé (PID $FRONT_PID)"

# Attendre les deux processus (optionnel)
wait $BACK_PID
wait $FRONT_PID


