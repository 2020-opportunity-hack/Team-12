class UsersTable {
	container;
	columnHeaders = ['Profile Picture', 'Name', 'Email', 'Family ID', 'Balance', 'Action'];
	columnKeys = ['imageUrl', 'name', 'email', 'familyId', 'balance', 'action'];
	actionOptions = ['', 'Withdraw', 'Deposit', 'View Transactions', 'Edit User', 'Delete User', 'Deactivate User'];
	constructor(data, elementId, actionCallback) {
		this.container = document.getElementById(elementId);
		this.container.innerHTML = '';
		this.setTableHeaders();
		this.updateData(data, actionCallback);
	}

	setTableHeaders() {
		let headerRow = document.createElement('tr');
		for(var i in this.columnHeaders) {
			let curCol = this.columnHeaders[i],
				colElem = document.createElement('th');
			colElem.innerHTML = curCol;
			headerRow.appendChild(colElem);
		}
		this.container.appendChild(headerRow);
	}

	getActionData(uniqueId, actionCallback) {
		let dropdownElem = document.createElement('select');
		for(let i in this.actionOptions) {
			let curOption = this.actionOptions[i],
				optionElem = document.createElement('option');
			optionElem.innerHTML = curOption;
			optionElem.setAttribute('value', i);
			dropdownElem.appendChild(optionElem);
		}
		dropdownElem.addEventListener('change', function(ev) {
			actionCallback(uniqueId, this.value, ev);
			this.value = '';
		});
		return dropdownElem;
	}

	updateData(data, actionCallback) {
		for(let rowIndex in data) {
			let curRow = data[rowIndex],
				rowElem = document.createElement('tr');
			for(let i in this.columnKeys) {
				let curColumnName = this.columnKeys[i],
					columnData = '',
					colElem = document.createElement('td');
				if(curRow.hasOwnProperty(curColumnName)) {
					columnData = curRow[curColumnName];
				}
				if(curColumnName == 'action') {
					// add action dropdown
					let actionDropdown = this.getActionData(curRow, actionCallback)
					colElem.appendChild(actionDropdown);
					// bind callback
				} else if(curColumnName == 'imageUrl') {
					let imageElem = document.createElement('img');
					imageElem.width = '100';
					imageElem.height = '100';
					if(curRow[curColumnName] == null || curRow[curColumnName] == 'null') {
						imageElem.src = "images/default_img.png";
					} else {
						imageElem.src = curRow[curColumnName];
					}
					colElem.appendChild(imageElem);
				} else {
					colElem.innerHTML = columnData;
				}
				rowElem.appendChild(colElem);
			}
			this.container.appendChild(rowElem);
		}
	}
}