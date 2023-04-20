import { Table } from 'antd';

function MyTable() {
  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      className: 'font-bold',
    },
    {
      title: 'Age',
      dataIndex: 'age',
      key: 'age',
      className: 'text-center',
    },
    {
      title: 'Address',
      dataIndex: 'address',
      key: 'address',
      className: 'text-right',
    },
    {
      title: 'Phone',
      dataIndex: 'phone',
      key: 'phone',
      className: 'text-right',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
      className: 'text-right',
    },
  ];

  const data = [
    {
      key: '1',
      name: 'John Brown',
      age: 32,
      address: 'New York No. 1 Lake Park',
      phone: '123-456-7890',
      email: 'john.brown@example.com',
    },
    {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park',
      phone: '234-567-8901',
      email: 'jim.green@example.com',
    },
    {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park',
      phone: '345-678-9012',
      email: 'joe.black@example.com',
    },
  ];

  return (
    <Table columns={columns} dataSource={data} className="w-full">
      {/* Optional: add custom components or additional data */}
      <Table.Column
        title="Action"
        key="action"
        className="text-center"
        render={(text, record) => (
          <div className="flex justify-center space-x-4">
            <button className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded">
              Edit
            </button>
            <button className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded">
              Delete
            </button>
          </div>
        )}
      />
      <Table.Column title="Notes" dataIndex="notes" key="notes" />
    </Table>
  );
}

export default MyTable;
