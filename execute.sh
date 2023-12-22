# Create database
username=$(cat config_database.txt | grep username | cut -d "=" -f2)
password=$(cat config_database.txt | grep password | cut -d "=" -f2)

mysql -u $username -p$password create notes;

echo "Database notes created correctly."

# Configure application.properties
source_file="config_app_properties.txt"
dest_file="backend/src/main/resources/application.properties"

cat "$source_file" > "$dest_file"

echo "File applications_properties configured correctly."

#Run backend
./backend.sh

# Run frontend
./frontend.sh

