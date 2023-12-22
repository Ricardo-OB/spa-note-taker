# Run frontend
vue_project_dir="frontend"
cd "$vue_project_dir" || exit

npm install
npm run serve

echo "Running frontend correctly."