#!/bin/bash

# Configuration
DB_HOST="localhost"
DB_PORT="5432"
DB_NAME="library"
DB_USER="postgres"

# Colors
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo -e "${GREEN}Starting Database Setup for Library Management System...${NC}"

# Check if psql is installed
if ! command -v psql &> /dev/null; then
    echo "Error: 'psql' is not installed or not in your PATH."
    exit 1
fi

# Prompt for password (optional, psql handles it via .pgpass or prompt)
echo "Connecting to $DB_NAME as $DB_USER..."

# Run Scripts
# 00-clean.sql
echo -e "${GREEN}1. Cleaning Database (00-clean.sql)...${NC}"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f schema/00-clean.sql

# 01-tables.sql
echo -e "${GREEN}2. Creating Tables (01-tables.sql)...${NC}"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f schema/01-tables.sql

# 02-constraints.sql
echo -e "${GREEN}3. Adding Constraints (02-constraints.sql)...${NC}"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f schema/02-constraints.sql

# 03-triggers.sql
echo -e "${GREEN}4. Creating Triggers (03-triggers.sql)...${NC}"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f schema/03-triggers.sql

# 04-data.sql
echo -e "${GREEN}5. Seeding Data (04-data.sql)...${NC}"
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f schema/04-data.sql

echo -e "${GREEN}Database Setup Complete!${NC}"
