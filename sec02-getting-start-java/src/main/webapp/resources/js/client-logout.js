document.addEventListener("DOMContentLoaded", () => {
	const logoutBtn = document.getElementById("logoutBtn")
	logoutBtn.addEventListener("click", () => {
		document.location = `http://demo@${document.location.host}`
	})
})