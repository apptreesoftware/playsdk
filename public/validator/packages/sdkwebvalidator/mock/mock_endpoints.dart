library sdkwebvalidator.mock_endpoints;

var mockEndpoints = """
{
    "success": true,
    "records": [
        {
            "endpoint": "dataset/timeentry",
            "name": "Time Entry",
            "type": "data",
            "url": "http://localhost:9000/dataset/timeentry"
        },
        {
            "endpoint": "dataset/closewo",
            "name": "Close Work Order",
            "type": "data",
            "url": "http://localhost:9000/dataset/closewo"
        },
        {
            "endpoint": "dataset/workorders",
            "name": "Work Order",
            "type": "data",
            "url": "http://localhost:9000/dataset/workorders"
        },
        {
            "endpoint": "dataset/completewo",
            "name": "Complete Work Order",
            "type": "data",
            "url": "http://localhost:9000/dataset/completewo"
        },
        {
            "endpoint": "list/paycode",
            "name": "Pay Code",
            "type": "list",
            "url": "http://localhost:9000/list/paycode"
        },
        {
            "endpoint": "list/wostatus",
            "name": "WO Status",
            "type": "list",
            "url": "http://localhost:9000/list/wostatus"
        },
        {
            "endpoint": "list/craft",
            "name": "Craft",
            "type": "list",
            "url": "http://localhost:9000/list/craft"
        },
        {
            "endpoint": "list/outageclass",
            "name": "Outage Class",
            "type": "list",
            "url": "http://localhost:9000/list/outageclass"
        },
        {
            "endpoint": "list/employee",
            "name": "Employee",
            "type": "list",
            "url": "http://localhost:9000/list/employee"
        },
        {
            "endpoint": "list/building",
            "name": "Building",
            "type": "list",
            "url": "http://localhost:9000/list/building"
        },
        {
            "endpoint": "list/method",
            "name": "Method",
            "type": "list",
            "url": "http://localhost:9000/list/method"
        },
        {
            "endpoint": "list/equipment",
            "name": "Equipment",
            "type": "list",
            "url": "http://localhost:9000/list/equipment"
        },
        {
            "endpoint": "list/accountsegment1",
            "name": "Account Segment 1",
            "type": "list",
            "url": "http://localhost:9000/list/accountsegment1"
        },
        {
            "endpoint": "list/priority",
            "name": "Priority",
            "type": "list",
            "url": "http://localhost:9000/list/priority"
        },
        {
            "endpoint": "list/accountsegment2",
            "name": "Account Segment 2",
            "type": "list",
            "url": "http://localhost:9000/list/accountsegment2"
        },
        {
            "endpoint": "list/accountsegment3",
            "name": "Account Segment 3",
            "type": "list",
            "url": "http://localhost:9000/list/accountsegment3"
        },
        {
            "endpoint": "list/accountsegment4",
            "name": "Account Segment 4",
            "type": "list",
            "url": "http://localhost:9000/list/accountsegment4"
        },
        {
            "endpoint": "list/accountsegment5",
            "name": "Account Segment 5",
            "type": "list",
            "url": "http://localhost:9000/list/accountsegment5"
        },
        {
            "endpoint": "list/accountsegment6",
            "name": "Account Segment 6",
            "type": "list",
            "url": "http://localhost:9000/list/accountsegment6"
        },
        {
            "endpoint": "list/attribute9",
            "name": "WO Attribute 9",
            "type": "list",
            "url": "http://localhost:9000/list/attribute9"
        },
        {
            "endpoint": "list/attribute8",
            "name": "WO Attribute 8",
            "type": "list",
            "url": "http://localhost:9000/list/attribute8"
        },
        {
            "endpoint": "list/maintenancetype",
            "name": "Maintenance Type",
            "type": "list",
            "url": "http://localhost:9000/list/maintenancetype"
        },
        {
            "endpoint": "list/attribute5",
            "name": "WO Attribute 5",
            "type": "list",
            "url": "http://localhost:9000/list/attribute5"
        },
        {
            "endpoint": "list/attribute4",
            "name": "WO Attribute 4",
            "type": "list",
            "url": "http://localhost:9000/list/attribute4"
        },
        {
            "endpoint": "list/laborworkorders",
            "name": "Labor Work Orders",
            "type": "list",
            "url": "http://localhost:9000/list/laborworkorders"
        },
        {
            "endpoint": "list/attribute7",
            "name": "WO Attribute 7",
            "type": "list",
            "url": "http://localhost:9000/list/attribute7"
        },
        {
            "endpoint": "list/attribute6",
            "name": "WO Attribute 6",
            "type": "list",
            "url": "http://localhost:9000/list/attribute6"
        },
        {
            "endpoint": "list/attribute1",
            "name": "WO Attribute 1",
            "type": "list",
            "url": "http://localhost:9000/list/attribute1"
        },
        {
            "endpoint": "list/attribute14",
            "name": "WO Attribute 14",
            "type": "list",
            "url": "http://localhost:9000/list/attribute14"
        },
        {
            "endpoint": "list/attribute13",
            "name": "WO Attribute 13",
            "type": "list",
            "url": "http://localhost:9000/list/attribute13"
        },
        {
            "endpoint": "list/attribute3",
            "name": "WO Attribute 3",
            "type": "list",
            "url": "http://localhost:9000/list/attribute3"
        },
        {
            "endpoint": "list/attribute12",
            "name": "WO Attribute 12",
            "type": "list",
            "url": "http://localhost:9000/list/attribute12"
        },
        {
            "endpoint": "list/attribute2",
            "name": "WO Attribute 2",
            "type": "list",
            "url": "http://localhost:9000/list/attribute2"
        },
        {
            "endpoint": "list/attribute11",
            "name": "WO Attribute 11",
            "type": "list",
            "url": "http://localhost:9000/list/attribute11"
        },
        {
            "endpoint": "list/attribute10",
            "name": "WO Attribute 10",
            "type": "list",
            "url": "http://localhost:9000/list/attribute10"
        },
        {
            "endpoint": "list/attribute19",
            "name": "WO Attribute 19",
            "type": "list",
            "url": "http://localhost:9000/list/attribute19"
        },
        {
            "endpoint": "list/attribute18",
            "name": "WO Attribute 18",
            "type": "list",
            "url": "http://localhost:9000/list/attribute18"
        },
        {
            "endpoint": "list/attribute17",
            "name": "WO Attribute 17",
            "type": "list",
            "url": "http://localhost:9000/list/attribute17"
        },
        {
            "endpoint": "list/attribute16",
            "name": "WO Attribute 16",
            "type": "list",
            "url": "http://localhost:9000/list/attribute16"
        },
        {
            "endpoint": "list/altrequestor",
            "name": "Alt Requestor",
            "type": "list",
            "url": "http://localhost:9000/list/altrequestor"
        },
        {
            "endpoint": "list/attribute15",
            "name": "WO Attribute 15",
            "type": "list",
            "url": "http://localhost:9000/list/attribute15"
        },
        {
            "endpoint": "list/crew",
            "name": "Crew",
            "type": "list",
            "url": "http://localhost:9000/list/crew"
        },
        {
            "endpoint": "list/laborclass",
            "name": "Labor Class",
            "type": "list",
            "url": "http://localhost:9000/list/laborclass"
        },
        {
            "endpoint": "list/attribute25",
            "name": "WO Attribute 25",
            "type": "list",
            "url": "http://localhost:9000/list/attribute25"
        },
        {
            "endpoint": "list/attribute24",
            "name": "WO Attribute 24",
            "type": "list",
            "url": "http://localhost:9000/list/attribute24"
        },
        {
            "endpoint": "list/laborstatus",
            "name": "Labor Status",
            "type": "list",
            "url": "http://localhost:9000/list/laborstatus"
        },
        {
            "endpoint": "list/attribute23",
            "name": "WO Attribute 23",
            "type": "list",
            "url": "http://localhost:9000/list/attribute23"
        },
        {
            "endpoint": "list/attribute22",
            "name": "WO Attribute 22",
            "type": "list",
            "url": "http://localhost:9000/list/attribute22"
        },
        {
            "endpoint": "list/floor",
            "name": "Floor",
            "type": "list",
            "url": "http://localhost:9000/list/floor"
        },
        {
            "endpoint": "list/department",
            "name": "Department",
            "type": "list",
            "url": "http://localhost:9000/list/department"
        },
        {
            "endpoint": "list/attribute21",
            "name": "WO Attribute 21",
            "type": "list",
            "url": "http://localhost:9000/list/attribute21"
        },
        {
            "endpoint": "list/attribute20",
            "name": "WO Attribute 20",
            "type": "list",
            "url": "http://localhost:9000/list/attribute20"
        },
        {
            "endpoint": "list/attribute29",
            "name": "WO Attribute 29",
            "type": "list",
            "url": "http://localhost:9000/list/attribute29"
        },
        {
            "endpoint": "list/attribute28",
            "name": "WO Attribute 28",
            "type": "list",
            "url": "http://localhost:9000/list/attribute28"
        },
        {
            "endpoint": "list/attribute27",
            "name": "WO Attribute 27",
            "type": "list",
            "url": "http://localhost:9000/list/attribute27"
        },
        {
            "endpoint": "list/attribute26",
            "name": "WO Attribute 26",
            "type": "list",
            "url": "http://localhost:9000/list/attribute26"
        },
        {
            "endpoint": "list/requestor",
            "name": "Requestor",
            "type": "list",
            "url": "http://localhost:9000/list/requestor"
        },
        {
            "endpoint": "list/assignedto",
            "name": "Assigned To",
            "type": "list",
            "url": "http://localhost:9000/list/assignedto"
        },
        {
            "endpoint": "list/room",
            "name": "Room",
            "type": "list",
            "url": "http://localhost:9000/list/room"
        },
        {
            "endpoint": "list/site",
            "name": "Site",
            "type": "list",
            "url": "http://localhost:9000/list/site"
        },
        {
            "endpoint": "list/attribute30",
            "name": "WO Attribute 30",
            "type": "list",
            "url": "http://localhost:9000/list/attribute30"
        },
        {
            "endpoint": "user",
            "name": "User Info",
            "type": "user info",
            "url": "http://localhost:9000/user"
        },
        {
            "endpoint": "auth",
            "name": "Authentication",
            "type": "authentication",
            "url": "http://localhost:9000/auth"
        },
        {
            "endpoint": "attachment",
            "name": "Attachment",
            "type": "Attachment",
            "url": "http://localhost:9000/attachments"
        }
    ]
}
""";