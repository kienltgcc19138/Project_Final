export const formatDateTime = (date) => {
  let m = new Date(date);
  let day = m.getDate() < 10 ? `0${m.getDate()}` : m.getDate();
  let month = m.getMonth() + 1 < 10 ? `0${m.getMonth() + 1}` : m.getMonth() + 1;
  let year = m.getFullYear();
  let hours = m.getHours() < 10 ? `0${m.getHours()}` : m.getHours();
  let minus = m.getMinutes() < 10 ? `0${m.getMinutes()}` : m.getMinutes();
  let dateString = day + "/" + month + "/" + year + " " + hours + ":" + minus;
  return dateString;
};
export const formatDate = (date) => {
  let m = new Date(date);
  let day = m.getDate() < 10 ? `0${m.getDate()}` : m.getDate();
  let month = m.getMonth() + 1 < 10 ? `0${m.getMonth() + 1}` : m.getMonth() + 1;
  let year = m.getUTCFullYear();
  let dateString = day + "/" + month + "/" + year + " ";
  return dateString;
};

export const changeDate = (x) => {
	if (x === null || x === undefined || x === '' ) return new Date();
	let temp = new Date(x);
	x = `${temp.getDate()}/${temp.getMonth() + 1}/${temp.getFullYear()}`;
	let array = x.split('/');
	return array[1] + '/' + array[0] + '/' + array[2];
};