const signUpBtn = document.getElementById('signUpBtn');
const signInBtn = document.getElementById('signInBtn');
const container = document.querySelector('.container');

signUpBtn.addEventListener('click', () => {
    container.classList.add('sign-up-mode');
});

signInBtn.addEventListener('click', () => {
    container.classList.remove('sign-up-mode');
});

// Demo login/signup validation
document.getElementById('loginForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    const loginError = document.getElementById('loginError');

    if(username === "admin" && password === "admin123") {
        alert("Login Success!");
    } else {
        loginError.textContent = "Invalid username or password";
    }
});

document.getElementById('signupForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const username = document.getElementById('signupUsername').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;
    const signupError = document.getElementById('signupError');

    if(username && email && password) {
        alert("Signup Success!");
    } else {
        signupError.textContent = "All fields are required";
    }
});
function handleSignUpForm()
{
    document.getElementById("signUpPanel").style.display="none";
    document.getElementById("signInPanel").style.display="block";
    document.getElementById("signInForm").style.display="none";
    document.getElementById("signUpForm").style.display="block";

}
function handleSignInForm()
{
    document.getElementById("signUpPanel").style.display="block";
    document.getElementById("signInPanel").style.display="none";
    document.getElementById("signInForm").style.display="block";
    document.getElementById("signUpForm").style.display="none";
}