# Run backend
project_dir="backend"
cd "$project_dir" || exit

execute_java=true

if [ -f "mvnw" ]; then
    ./mvnw clean
    ./mvnw spring-boot:run
    if [ $? -eq 0 ]; then
        execute_java=false
    fi
elif [ -f "mvnw.cmd" ]; then
    ./mvnw.cmd clean
    ./mvnw.cmd spring-boot:run
    if [ $? -eq 0 ]; then
        execute_java=false
    fi
else
    echo "Maven Wrapper (mvnw) not found."
fi

if [ "$execute_java" = true ]; then
    java -jar target/app-0.0.1-SNAPSHOT.jar com.notes.app.AppApplication

    if [ $? -eq 0 ]; then
        echo "Running Java application correctly."
    else
        echo "Error running Java application."
    fi
fi
