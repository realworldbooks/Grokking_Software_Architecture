Python (FastAPI)
**Path:** `/Python/python_app/README.md`


# AI API Demo (Python / FastAPI)

This project demonstrates how to apply the "Code-as-Prompt" architecture in the Python ecosystem. 
We use **FastAPI**, which automatically translates Python docstrings (`""" ... """`) and Pydantic models into an AI-ready OpenAPI specification.

### Prerequisites
* Python 3.8+ installed.

### How to Run
1. Open your terminal and navigate to the `python_app` directory.
2. (Optional but recommended) Create and activate a virtual environment:
   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows use: venv\Scripts\activate
   ```
3. Install the required dependencies: `pip install -r requirements.txt`
4. Start the Uvicorn web server: `uvicorn main:app --reload`
5. Open your browser and navigate to the interactive AI prompt UI: http://localhost:8000/docs
