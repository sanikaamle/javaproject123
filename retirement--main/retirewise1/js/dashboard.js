// Check if user is logged in
const token = localStorage.getItem('token');
const userEmail = localStorage.getItem('userEmail');
if (!token || !userEmail) {
    window.location.href = 'login.html';
}

// Logout functionality
document.getElementById('logoutBtn').addEventListener('click', () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    window.location.href = 'login.html';
});

// Handle retirement form submission
document.getElementById('retirementForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = {
        email: userEmail,
        currentAge: parseInt(document.getElementById('currentAge').value),
        retirementAge: parseInt(document.getElementById('retirementAge').value),
        monthlyIncome: parseFloat(document.getElementById('monthlyIncome').value),
        savings: parseFloat(document.getElementById('savings').value),
        riskTolerance: document.getElementById('riskTolerance').value
    };

    try {
        const response = await fetch('http://localhost:8080/api/recommendations', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            const recommendations = await response.json();
            displayRecommendations(recommendations);
        } else {
            alert('Failed to get recommendations. Please try again.');
        }
    } catch (error) {
        console.error('Recommendation error:', error);
        alert('An error occurred while getting recommendations. Please try again.');
    }
});

// Display recommendations
function displayRecommendations(recommendations) {
    const schemesList = document.querySelector('.schemes-list');
    schemesList.innerHTML = '';

    recommendations.forEach(scheme => {
        const schemeCard = document.createElement('div');
        schemeCard.className = 'scheme-card';
        schemeCard.innerHTML = `
            <h3>${scheme.name}</h3>
            <p>Monthly contribution: $${scheme.monthlyContribution}</p>
            <p>Expected returns: ${scheme.expectedReturns}% annually</p>
            <p>Risk level: ${scheme.riskLevel}</p>
            <button class="view-details" onclick="viewSchemeDetails('${scheme.id}')">View Details</button>
        `;
        schemesList.appendChild(schemeCard);
    });
}

// View scheme details
async function viewSchemeDetails(schemeId) {
    try {
        const response = await fetch(`/api/schemes/${schemeId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const details = await response.json();
            // Display scheme details in a modal or new page
            alert(`Scheme Details:\nName: ${details.name}\nDescription: ${details.description}\nMinimum Investment: $${details.minimumInvestment}\nLock-in Period: ${details.lockInPeriod} years`);
        } else {
            alert('Failed to fetch scheme details. Please try again.');
        }
    } catch (error) {
        console.error('Scheme details error:', error);
        alert('An error occurred while fetching scheme details. Please try again.');
    }
}