import React from "react";

const ModalConfirm = ({ show, title, handleCancel, handleConfirm }) => {
  return (
    <React.Fragment>
      {show && (
        <div
          className="fixed inset-0 z-[9999] overflow-y-auto"
          // aria-labelledby="modal-title"
          // aria-modal="true"
        >
          <div className="flex items-center justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
            <div
              className="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-75"
              // aria-hidden="true"
              onClick={handleCancel}
            ></div>
            <span
              className="hidden sm:inline-block sm:align-middle sm:h-screen"
              // aria-hidden="true"
            >
              &#8203;
            </span>
            <div className="relative inline-block w-full m-auto lg:w-[30%]">
              <div className="relative w-full h-full max-w-md p-4 md:h-auto">
                <div className="relative bg-white rounded-lg shadow ">
                  <button
                    type="button"
                    className="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center"
                    // data-modal-toggle="popup-modal"
                    onClick={handleCancel}
                  >
                    <svg
                      className="w-5 h-5"
                      fill="currentColor"
                      viewBox="0 0 20 20"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        fillRule="evenodd"
                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                        clipRule="evenodd"
                      ></path>
                    </svg>
                  </button>
                  <div className="p-6 text-center">
                    <svg
                      className="mx-auto mb-4 text-gray-400 w-14 h-14 "
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                      ></path>
                    </svg>
                    <h3 className="mb-5 text-base font-normal text-gray-500 lg:text-lg ">
                      {title}
                    </h3>
                    <button
                      // data-modal-toggle="popup-modal"
                      type="button"
                      className="mr-2 text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-md border border-gray-200 text-sm font-medium px-7 py-2.5 hover:text-white focus:z-10 "
                      onClick={handleCancel}
                    >
                     Cancel
                    </button>
                    <button
                      // data-modal-toggle="popup-modal"
                      type="button"
                      className="text-white bg-green-600 hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-md text-sm inline-flex items-center px-5 py-2.5 text-center"
                      onClick={handleConfirm}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </React.Fragment>
  );
};

export default ModalConfirm;
